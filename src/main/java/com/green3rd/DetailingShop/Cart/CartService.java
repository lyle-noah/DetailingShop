package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // 유저 장바구니 조회
    public Cart getUser(SiteUser user) {
        return cartRepository.findByUser(user).orElse(null);
    }

    // 장바구니 상품 추가
    public void addCart(SiteUser user, String productId) {
        Integer intProductId = Integer.parseInt(productId);  // String -> Integer 변환
        Product product = productRepository.findById(intProductId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            cart = new Cart();
            // setUser 메서드를 사용해 SiteUser 설정
            cart.setUser(user);
            cart.setProducts(new ArrayList<>());  // 새로 생성된 Cart에 빈 리스트로 초기화
        }

        // 중복 상품 체크
        if (!cart.getProducts().contains(product)) {
            cart.getProducts().add(product);
        }

        // 장바구니 제품 목록 가져오기
        List<Product> products = cart.getProducts();
        products.add(product);
        cartRepository.save(cart);
    }
}
