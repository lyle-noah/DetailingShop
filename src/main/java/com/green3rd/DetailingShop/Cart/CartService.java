package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByUser(SiteUser siteUser) {
        return cartRepository.findBySiteUser(siteUser);
    }

    public Cart addProductToCart(SiteUser siteUser, Long productId) {
        Cart cart = cartRepository.findBySiteUser(siteUser);
        if (cart == null) {
            cart = new Cart();
            cart.setSiteUser(siteUser);
        }

        Optional<Product> productOpt = productRepository.findById(productId.intValue());
        if (productOpt.isPresent()) {
            cart.getProducts().add(productOpt.get());
        } else {
            throw new RuntimeException("Product not found");
        }

        return cartRepository.save(cart);
    }
}
