package com.green3rd.DetailingShop.Cart;

import org.springframework.stereotype.Controller;

@Controller
public class CartController {
    private  final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
        
    }
}


