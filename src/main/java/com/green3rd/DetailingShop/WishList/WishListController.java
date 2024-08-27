package com.green3rd.DetailingShop.WishList;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import com.green3rd.DetailingShop.UserLike.UserLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishListController {

    private final UserLikesService userLikesService;
    private final UserService userService;

    @GetMapping("")
    public String getUserWishList(Model model) {
        // SecurityContextHolder에서 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // userDetails에서 username을 가져와서 User 엔티티를 조회
        User user = userService.findByUsername(userDetails.getUsername());
        Integer userId = user.getId();

        List<Product> likedProducts = userLikesService.getLikedProductsByUser(userId);
        model.addAttribute("productsInfor", likedProducts);
        return "mypage/wishlist"; // wishlist.html을 반환
    }

        @PostMapping("/remove/{indexId}")
        public String removeProductFromWishlist(@PathVariable("indexId") Integer indexId) {
            // 현재 로그인된 사용자 ID 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());

            // likeState를 false로 설정하여 위시리스트에서 제거
            userLikesService.updateLikeState(user.getId(), indexId, false);

            return "redirect:/wishlist"; // 위시리스트 페이지로 리다이렉트
    }
}