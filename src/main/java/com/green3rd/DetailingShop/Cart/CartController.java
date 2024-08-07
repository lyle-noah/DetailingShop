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

    @GetMapping("/cart")
    public String getCartPage(@RequestParam Long id, Model model) {
        SiteUser siteUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getCartByUser(siteUser);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam Long userId) {
        SiteUser siteUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        cartService.addProductToCart(siteUser, productId);
        return "redirect:/cart?id=" + userId; // 상품 추가 후 장바구니 페이지로 리다이렉션
    }
}
