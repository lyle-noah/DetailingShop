package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.LoginUser.UserRepository;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public void addCart(SiteUser user, String productId) {
        Cart cart = cartRepository.findBySiteUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setSiteUser(user);
        }
        Optional<Product> productOptional = productRepository.findById(Integer.parseInt(productId));
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            cart.getProducts().add(product);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("상품을 찾을 수 없습니다.");
        }
    }
}
