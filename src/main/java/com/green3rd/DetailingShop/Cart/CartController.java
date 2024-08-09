package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.LoginUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public CartController(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    // 장바구니 페이지
    @GetMapping("/cart")
    public String CartPage(@RequestParam(name = "userId",defaultValue = "1") Long userId, Model model) {
        SiteUser user = userRepository.findById(userId).orElse(null);
        Cart cart = cartService.getUser(user);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    // 장바구니 상품 추가
    @PostMapping("/cart/add")
    public String addCart(@RequestParam Long userId, @RequestParam String productId) {
        SiteUser user = userRepository.findById(userId).orElse(null);
        cartService.addCart(user, productId);
        return "redirect:/cart?userId=" + userId;
    }
}
