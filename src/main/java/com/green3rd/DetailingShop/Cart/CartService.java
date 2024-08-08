package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return cartRepository.findBySiteUser(user);
    }
    // 상품 추가
    public Cart addCart(SiteUser user, String productId) {
        Cart cart = cartRepository.findBySiteUser(user);
        // 장바구니 없을 시 새로 생성
        if (cart == null) {
            cart = new Cart();
            cart.setSiteUser(user);
        }

        // 모든 제품을 검색하고, 해당 productId를 가진 제품을 필터링
        List<Product> products = productRepository.findAll();
        Optional<Product> productOpt = products.stream()
                .filter(product -> productId.equals(product.getProductId()))
                .findFirst();

        // 제품 존재 시 장바구니 추가
        if (productOpt.isPresent()) {
            cart.getProducts().add(productOpt.get());
        } else {
            throw new RuntimeException("상품이 없습니다.");
        }

        return cartRepository.save(cart);
    }
}
