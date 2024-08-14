package com.green3rd.DetailingShop.ProductList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.http.HttpSession; // 추가된 import
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList; // 추가된 import
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final RequestCache requestCache = new HttpSessionRequestCache();

    @GetMapping("/products")
    public String getProductByCategory(
            // 상품 목록 페이지를 반환합니다.
            @RequestParam(required = false, defaultValue = "") String firstCategory, // 첫 번째 카테고리 필터
            @RequestParam(required = false, defaultValue = "") String secondCategory, // 두 번째 카테고리 필터
            @RequestParam(required = false, defaultValue = "") String thirdCategory, // 세 번째 카테고리 필터
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "10") int size, // 페이지 크기 (기본값: 10)
            Model model) { // 위 내용들을 뷰에 전달할 데이터 모델

        Page<Product> productsPage = productService.getProductsByCategory(firstCategory, secondCategory, thirdCategory, page, size);
        // DB에서 가격정보 포멧은 int형으로 단순 10000 이런식으로 표기되어있기에 원화표시의 포멧을 넣기위한 설정임.
        productsPage.forEach(product -> product.setFormattedPrice(productService.formatPrice(product.getProductPrice())));

        // 페이지네이션 정보
        int currentPage = productsPage.getNumber();
        int totalPages = productsPage.getTotalPages();
        List<Product> products = productsPage.getContent();

        // 페이지네이션 범위 계산
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        if (endPage - startPage < 4) {
            if (startPage == 0) {
                endPage = Math.min(totalPages - 1, startPage + 4);
            } else if (endPage == totalPages - 1) {
                startPage = Math.max(0, endPage - 4);
            }
        }

        // 상품 수량 정보
        int totalProducts = (int) productsPage.getTotalElements();

        // 제품의 데이터를 모델에 전달
        model.addAttribute("productsInfor", products);
        model.addAttribute("firstCategoryName", firstCategory);
        model.addAttribute("secondCategoryName", secondCategory);
        model.addAttribute("thirdCategoryName", thirdCategory);

        // 제품 페이지 정보와 연산 정보값을 모델에 전달
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", size);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 제품의 수량 데이터를 모델에 전달
        model.addAttribute("totalProducts", totalProducts);

        if (!thirdCategory.isEmpty()) {
            return "category/thirdCategory";
        } else if (!secondCategory.isEmpty()) {
            return "category/secondCategory";
        } else {
            return "category/firstCategory";
        }
    }

    // 상세 페이지 조회
    @GetMapping("/product/{indexId}")
    public String getProductDetail(@PathVariable int indexId, Model model, HttpSession session) {
        Product product = productService.getProductByID(indexId);
        if (product == null) {
            return "error/404";
        }
        product.setFormattedPrice(productService.formatPrice(product.getProductPrice()));
        model.addAttribute("product", product);

        // 최근 본 상품 리스트를 세션에서 가져옴
        List<Product> recentProducts = (List<Product>) session.getAttribute("recentProducts");
        if (recentProducts == null) {
            recentProducts = new LinkedList<>();
        }

        // 이미 리스트에 있는지 확인 후, 없으면 추가
        boolean alreadyViewed = recentProducts.stream()
                .anyMatch(p -> p.getIndexId() == product.getIndexId());
        if (!alreadyViewed) {
            if (recentProducts.size() >= 5) {
                recentProducts.remove(0); // 최대 5개의 최근 본 상품을 유지하기 위해 리스트의 첫 번째 요소 제거
            }
            recentProducts.add(product);
        }

        // 세션에 업데이트된 리스트 저장
        session.setAttribute("recentProducts", recentProducts);

        return "main/detail";
    }

    // 좋아요 버튼 기능
    @PostMapping("/product/like/{indexId}")
    public String toggleLike(@PathVariable int indexId,
                             @RequestHeader("Referer") String referer,
                             HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName().equals("anonymousUser")) {
            // 사용자가 로그인하지 않은 경우, 요청 URL을 세션에 저장
            requestCache.saveRequest(request, response);
            model.addAttribute("message", "로그인 후 관심상품에 등록해주세요");
            model.addAttribute("url", "/user/login");
            return "alert/alertMessage_form01";
        }

        // 사용자가 로그인된 상태일 경우 좋아요 상태를 토글
        productService.toggleLikeState(indexId);
        return "redirect:" + referer;
    }
}
