package com.green3rd.DetailingShop.ProductList;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import com.green3rd.DetailingShop.UserLike.UserLikesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession; // 추가된 import
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final UserLikesService userLikesService;

    @GetMapping("/products")
    public String getProductByCategory(
            // 상품 목록 페이지를 반환합니다.
            @RequestParam(required = false, defaultValue = "") String firstCategory, // 첫 번째 카테고리 필터
            @RequestParam(required = false, defaultValue = "") String secondCategory, // 두 번째 카테고리 필터
            @RequestParam(required = false, defaultValue = "") String thirdCategory, // 세 번째 카테고리 필터
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "10") int size, // 페이지 크기 (기본값: 10)
            Model model,
            Principal principal,
            HttpServletRequest request) { // 위 내용들을 뷰에 전달할 데이터 모델

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

        // 로그인한 사용자의 likeState를 설정
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            for (Product product : products) {
                UserLikes userLikes = userLikesService.findByUserAndProduct(user, product).orElse(null);
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

        // 각 상품의 좋아요 수를 계산하여 모델에 추가
        Map<Integer, Integer> likesCountMap = new HashMap<>();
        for (Product product : products) {
            int indexId = product.getIndexId(); // 적절한 메서드로 변경
            int likesCount = productService.getLikesCountForProduct(indexId);
            likesCountMap.put(indexId, likesCount);
        }

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

        // 현재 요청 URI를 모델에 전달
        model.addAttribute("currentUri", currentUri);

        // 좋아요 카운트 수를 모델에 전달
        model.addAttribute("likesCountMap", likesCountMap);

        // View 선택
        if (!thirdCategory.isEmpty()) {
            return "category/thirdCategory";
        } else if (!secondCategory.isEmpty()) {
            return "category/secondCategory";
        } else {
            return "category/firstCategory";
        }
    }

    // 카테고리별로 상위 4개의 좋아요 상품을 반환
    @PostMapping("/product/category")
    @ResponseBody
    public List<ProductDto> getProductsByCategory(@RequestParam("category") String category) {
        return productService.getTopLikedProductsByCategory(category);
    }

    // 상세 페이지 조회
    @GetMapping("/detail/{indexId}")
    public String getProductDetail(@PathVariable int indexId,
                                   Principal principal,
                                   Model model,
                                   HttpSession session) {
        // 상품을 indexId로 조회
        Product product = productService.findByIndexId(indexId);

        // 상품이 존재하지 않는 경우 404 페이지로 리다이렉트
        if (product == null) {
            return "error/404";
        }

        // 상품의 가격을 포맷팅하여 설정
        product.setFormattedPrice(productService.formatPrice(product.getProductPrice()));

        // 로그인한 사용자의 likeState를 설정
        if (principal != null) {
            // 현재 로그인한 사용자 조회
            User user = userService.findByUsername(principal.getName());

            // 사용자가 해당 상품을 좋아요했는지 조회
            UserLikes userLikes = userLikesService.findByUserAndProduct(user, product).orElse(null);

            if (userLikes != null) {
                product.setLikeState(userLikes.isLikeState());
            }
        }

        // 상품 정보를 모델에 추가
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
    @ResponseBody
    public Map<String, Object> likeProduct(@PathVariable int indexId,
                                           @RequestParam("redirectUrl") String redirectUrl,
                                           Principal principal,
                                           Model model) {

        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            // 비로그인 상태: 리다이렉트 URL과 메시지를 반환
            response.put("redirect", "/user/login?redirectUrl=" + redirectUrl);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        // 로그인된 상태에서 좋아요 처리 로직
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            boolean likeState = productService.toggleLike(user, product);
            response.put("likeState", likeState);

            // 디버그 로그 추가
//            System.out.println("User: " + user.getUsername());
//            System.out.println("Product: " + product.getProductName());
//            System.out.println("Like State: " + likeState);
        }

        return response;
    }
}