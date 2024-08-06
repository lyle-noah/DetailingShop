package com.green3rd.DetailingShop.cart;

import com.green3rd.DetailingShop.user.SiteUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {
    private  final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
        
    }
}


