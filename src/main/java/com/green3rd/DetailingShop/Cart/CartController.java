package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.LoginUser.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // 장바구니 페이지
    @GetMapping("/cart")
    public String cartPage(@RequestParam("userId") Long userId, Model model) {
        // ID로 사용자 정보 조회
        SiteUser siteUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 사용자 장바구니 정보 가져오기
        Cart cart = cartService.getUser(siteUser);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    // 장바구니 상품 추가
    @PostMapping("/cart/add")
    @ResponseBody
    public Cart addCart(@RequestParam("userId") Long userId, @RequestParam("productId") String productId) {
        // ID로 사용자 정보 조회
        SiteUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다"));
        // 상품 추가
        return cartService.addCart(user, productId);
    }
}
