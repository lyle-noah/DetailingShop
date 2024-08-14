package com.green3rd.DetailingShop.ProductList;

import jakarta.servlet.http.HttpSession; // 추가된 import
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList; // 추가된 import
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String getProductByCategory(
            @RequestParam(required = false, defaultValue = "") String firstCategory,
            @RequestParam(required = false, defaultValue = "") String secondCategory,
            @RequestParam(required = false, defaultValue = "") String thirdCategory,
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "10") int size, // 페이지 크기 (기본값: 10)
            Model model) {

        Page<Product> productsPage = productService.getProductsByCategory(firstCategory, secondCategory, thirdCategory, page, size);
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

    @PostMapping("/product/like/{indexId}")
    public String toggleLike(@PathVariable int indexId, @RequestHeader("Referer") String referer) {
        productService.toggleLikeState(indexId);
        return "redirect:" + referer;
    }
}
