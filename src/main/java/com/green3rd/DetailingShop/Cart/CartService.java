package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.User.User;
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
    public Cart getUser(User user) {
        return cartRepository.findByUser(user).orElse(null);
    }

    // 장바구니 상품 추가
    public void addCart(User user, String productId) {
        Integer intProductId = Integer.parseInt(productId);  // String -> Integer 변환
        Product product = productRepository.findById(intProductId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

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
        cartRepository.save(cart);
    }

    // 장바구니 상품 수량 변경
    public void updateCart(User user, String productId, int quantity) {
        Integer intProductId = Integer.parseInt(productId);
        Cart cart = cartRepository.findByUser(user).orElse(null);

        if(cart != null && quantity > 0) {
            cart.getProducts().removeIf(product -> product.getProductId().equals(intProductId));
            for (int i = 0 ; i < quantity; i++) {
                Product product = productRepository.findById(intProductId)
                        .orElseThrow(()->
                                new IllegalArgumentException("상품을 찾을 수 없습니다."));
                cart.getProducts().add(product);
            }
            cartRepository.save(cart);
        }
    }

    // 장바구니 상품 삭제
    public void deleteCart(User user, String productId) {
        Integer intProductId = Integer.parseInt(productId);
        Cart cart = cartRepository.findByUser(user).orElse(null);

        if(cart != null) {
            cart.getProducts().removeIf(product -> product.getIndexId() == (intProductId));
            cartRepository.save(cart);
        }
    }

    // 장바구니 가격 계산
    public double totalPrice(Cart cart) {
        double totalPrice = 0;
        for (Product product : cart.getProducts()) {
            totalPrice += product.getProductPrice(); // 상품 가격을 총 가격에 추가
        }
        return totalPrice;
    }


}