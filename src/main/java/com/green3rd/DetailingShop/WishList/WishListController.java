package com.green3rd.DetailingShop.WishList;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.UserLike.UserLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishListController {

    private final UserLikesService userLikesService;

//    // 위시리스트 페이지로 이동하는 엔드포인트
//    @GetMapping("/wishlist")
//    public String getWishlist(Principal principal, Model model) {
//        // 현재 로그인된 사용자 정보에서 userId를 가져옵니다.
//        Long userId = getUserIdFromPrincipal(principal);
//        List<Product> likedProducts = userLikesService.getLikedProductsByUserId(userId);
//        model.addAttribute("likedProducts", likedProducts);
//        return "wishlist";  // 위시리스트 페이지로 리턴
//    }
//
//    private Long getUserIdFromPrincipal(Principal principal) {
//        // 실제 애플리케이션에서는 사용자 ID를 Principal 객체로부터 추출해야 합니다.
//        // 이를 위해 필요한 추가 구현이 필요할 수 있습니다.
//        return 1L;  // 예시로 임시 ID 반환
//    }
}
