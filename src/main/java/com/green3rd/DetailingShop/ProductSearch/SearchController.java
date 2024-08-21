package com.green3rd.DetailingShop.ProductSearch;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import com.green3rd.DetailingShop.UserLike.UserLikesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductService productService;
    private final SearchService searchService;
    private final UserService userService;
    private final UserLikesRepository userLikesRepository;

    @GetMapping("/search")
    public String searchProducts(@RequestParam("productName") String productName,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 Model model,
                                 Principal principal,
                                 HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = searchService.searchProducts(productName, pageable);
        // DB에서 가격정보 포멧은 int형으로 단순 10000 이런식으로 표기되어있기에 원화표시의 포멧을 넣기위한 설정임.
        productsPage.forEach(product -> product.setFormattedPrice(productService.formatPrice(product.getProductPrice())));

        if (productsPage.isEmpty()) {
            // 검색 결과가 없을 경우
            return "search/searchNoResult"; // 상품이 없다는 페이지로 리다이렉트
        }

        //페이지네이션 정보
        int totalPages = productsPage.getTotalPages();
        int currentPage = pageable.getPageNumber();
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);
        List<Product> products = productsPage.getContent();

        // 상품 수량 정보
        int totalProducts = (int)productsPage.getTotalElements();

        // 로그인한 사용자의 likeState를 설정
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            for (Product product : products) {
                UserLikes userLikes = userLikesRepository.findByUserAndProduct(user, product).orElse(null);
                if (userLikes != null) {
                    product.setLikeState(userLikes.isLikeState());
                }
                product.setFormattedPrice(productService.formatPrice(product.getProductPrice()));
            }
        } else {
            for (Product product : products) {
                product.setFormattedPrice(productService.formatPrice(product.getProductPrice()));
            }
        }

        // 현재 URL 요청을 저장, 쿼리 파라미터까지 포함하여 전체 URL을 저장
        StringBuilder currentUriBuilder = new StringBuilder(request.getRequestURL().toString());

        if (request.getQueryString() != null) {
            currentUriBuilder.append("?").append(request.getQueryString());
        }

        String currentUri = currentUriBuilder.toString();

        // 로그로 확인
        System.out.println("Current URI: " + currentUri);


        // 검색제품의 데이터를 모델에 전달
        model.addAttribute("searchResult", productsPage.getContent());
        model.addAttribute("query", productName);

        // 제품 페이지 정보와 연산 정보값을 모델에 전달
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 검색 제품의 수량 데이터를 모델에 전달
        model.addAttribute("totalProducts", totalProducts);

        // 현재 요청 URI를 모델에 전달
        model.addAttribute("currentUri", currentUri);

        return "search/searchResult";
    }

    // 상세 페이지 조회
    @GetMapping("/searchResult/{indexId}")
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
    @PostMapping("/searchResult/like/{indexId}")
    public String likeProduct(@PathVariable int indexId,
                              @RequestParam("redirectUrl") String redirectUrl,
                              Principal principal,
                              Model model) {
        if (principal == null) {
            // 로그인하지 않은 경우
            model.addAttribute("message", "로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?");
            model.addAttribute("urlYes", "/user/login?redirectUrl=" + redirectUrl);
            model.addAttribute("urlNo", redirectUrl); // 취소시 원래 페이지로 돌아가기

            // 알림 팝업 페이지로 이동
            return "alert/alertMessage_form01";
        }

        // 로그인한 경우 좋아요 처리 로직
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            productService.toggleLike(user, product);
        }

        // 좋아요 버튼을 눌렀던 페이지로 리다이렉트
        return "redirect:" + redirectUrl;
    }
}