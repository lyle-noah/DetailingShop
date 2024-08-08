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
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // 사용자 정보로 장바구니 조회
    public Cart getUser(SiteUser user) {
        Cart cart = cartRepository.findBySiteUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setSiteUser(user);
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    // 상품 추가
    public Cart addCart(SiteUser user, String productId) {
        Cart cart = getUser(user);

        // productId를 Integer로 변환
        Integer productIdInt;
        try {
            productIdInt = Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("상품 ID는 정수여야 합니다.");
        }

        // 특정 productId를 가진 제품을 검색
        Optional<Product> productOpt = productRepository.findById(productIdInt);

        // 제품 존재 시 장바구니 추가
        if (productOpt.isPresent()) {
            cart.getProducts().add(productOpt.get());
        } else {
            throw new RuntimeException("상품이 없습니다.");
        }

        return cartRepository.save(cart);
    }
}
