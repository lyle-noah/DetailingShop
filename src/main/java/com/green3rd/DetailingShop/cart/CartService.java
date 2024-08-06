package com.green3rd.DetailingShop.cart;

import org.springframework.stereotype.Service;

@Service
public class CartService {
    private  final  CartRepository cartRepository;
    private  final  CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository=cartRepository;
        this.cartItemRepository=cartItemRepository;

    }
}
