package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

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
        boolean productExists = false;
        for (Product p : cart.getProducts()) {
            if (p.getIndexId() == intProductId) {
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            product.setQuantity(1); // 상품의 초기 수량을 1로 설정
            cart.getProducts().add(product);
        }
        cartRepository.save(cart);
    }

    // 장바구니 상품 수량 변경
    public void updateCart(User user, String productId, int quantity) {
        Integer intProductId = Integer.parseInt(productId);
        Product product = productRepository.findById(intProductId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        if (quantity <= 0) {
            // 수량이 0 이하일 경우 해당 상품을 장바구니에서 제거
            cart.getProducts().remove(product);
        } else {
            // 상품이 이미 장바구니에 있는지 확인
            boolean productExists = false;
            for (Product p : cart.getProducts()) {
                if (p.getIndexId() == intProductId) {
                    p.setQuantity(quantity);
                    productExists = true;
                    break;
                }
            }

            // 상품이 장바구니에 없으면 추가하고 수량 설정
            if (!productExists) {
                product.setQuantity(quantity);
                cart.getProducts().add(product);
            }
        }
        cartRepository.save(cart);
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
            if (product.getQuantity() > 0) { // 수량이 0보다 큰 경우에만 총 금액 계산
                totalPrice += product.getProductPrice() * product.getQuantity();
            }
        }
        return totalPrice;
    }

    // 결제 후 장바구니 비우기
    public void clearCart(User user) {
        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart != null) {
            cart.getProducts().clear();
            cartRepository.save(cart);
        }
    }
}