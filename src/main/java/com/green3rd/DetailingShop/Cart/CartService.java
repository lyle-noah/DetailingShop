package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.ProductList.ProductRepository;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 특정 사용자의 장바구니 아이템 가져오기
    public List<Cart> getCartByUser(User user) {
        return cartRepository.findByUserAndCartStateTrue(user);
    }

    // 장바구니에 상품 추가
    public void toggleCartState(User user, Integer indexId) {
        Product product = productService.findByIndexId(indexId);
        Optional<Cart> optionalCart = cartRepository.findByUserAndProduct(user, product);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setCartState(!cart.isCartState());  // cart_state를 토글
            cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setCartState(true);  // 장바구니에 추가 (cart_state를 true로 설정)
            cart.setCartCount(1);  // 기본 수량은 1로 설정
            cartRepository.save(cart);
        }
    }

    // 장바구니에서 상품 수량 변경
    public void updateCartCount(User user, Integer indexId, int cartCount) {
        Product product = productService.findByIndexId(indexId);
        Optional<Cart> optionalCart = cartRepository.findByUserAndProduct(user, product);

        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.setCartCount(cartCount);  // 수량을 업데이트
            cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setCartState(true);  // 장바구니에 추가 (cart_state를 true로 설정)
            cart.setCartCount(cartCount);  // 입력된 수량으로 cart_count를 설정
            cartRepository.save(cart);
        }
    }

    // 장바구니 총 금액 계산
    public BigDecimal calculateTotalPrice(User user) {
        List<Cart> cartItems = cartRepository.findByUserAndCartStateTrue(user);
        return cartItems.stream()
                .map(item -> {
                    BigDecimal productPrice = BigDecimal.valueOf(item.getProduct().getProductPrice()); // 상품 가격이 BigDecimal 타입인지 확인
                    BigDecimal cartCount = BigDecimal.valueOf(item.getCartCount()); // 수량을 BigDecimal로 변환
                    return productPrice.multiply(cartCount); // 가격 * 수량
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 모든 항목의 가격을 합산
    }

    // 장바구니 품목 삭제
    public void deleteCartItem(User user, Product product) {
        Optional<Cart> cartItemOpt = cartRepository.findByUserAndProduct(user, product);
        if (cartItemOpt.isPresent()) {
            Cart cartItem = cartItemOpt.get();
            cartItem.setCartCount(0);
            cartItem.setCartState(false);
            cartRepository.save(cartItem);  // 저장을 통해 삭제 상태로 만듦
        } else {
            throw new RuntimeException("Cart item not found for deletion");
        }
    }

    // 장바구니 수량 업데이트
    public void updateCartItem(User user, Product product, int cartCount) {
        Optional<Cart> cartItemOpt = cartRepository.findByUserAndProduct(user, product);
        if (cartItemOpt.isPresent()) {
            Cart cartItem = cartItemOpt.get();
            cartItem.setCartCount(cartCount);
            cartRepository.save(cartItem);  // 수량 업데이트 후 저장
        } else {
            throw new RuntimeException("Cart item not found for update");
        }
    }


    public boolean addProductToCart(int indexId, int cartCount, String username) {
        // User와 Product 엔티티를 각각 조회합니다.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Product product = productRepository.findById(indexId)
                .orElseThrow(() -> new IllegalArgumentException("제품을 찾을 수 없습니다."));

        Optional<Cart> existingCartOpt = cartRepository.findByUserAndProduct(user, product);

        if (existingCartOpt.isPresent()) {
            // 장바구니에 이미 존재하는 경우
            Cart existingCart = existingCartOpt.get();
            existingCart.setCartCount(existingCart.getCartCount() + cartCount);
            cartRepository.save(existingCart);
            return false; // 이미 존재
        } else {
            // 새로 추가하는 경우
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setProduct(product);
            newCart.setCartCount(cartCount);
            cartRepository.save(newCart);
            return true; // 새로 추가
        }
    }

    // 사용자와 상품에 대한 장바구니 항목을 가져오거나 새로 생성
    public Cart getOrCreateCart(User user, Product product) {
        return cartRepository.findByUserAndProduct(user, product)
                .orElseGet(() -> new Cart(user, product));
    }

    // 장바구니 항목 저장
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}