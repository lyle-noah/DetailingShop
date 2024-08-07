package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.LoginUser.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping("/cart")
    public String moveCart(@RequestParam Long id, @RequestParam String ProductId) {
        Optional<SiteUser> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            SiteUser user = userOptional.get();
            cartService.addCart(user, ProductId);
            return "cart/cart/";
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
