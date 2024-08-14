package com.green3rd.DetailingShop.Wishlist;

import com.green3rd.DetailingShop.ProductList.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public Page<Product> getWishlistProducts(Pageable pageable) {
        return wishlistRepository.findByLikeStateTrue(pageable);
    }

    public String formatPrice(int price) {
        return String.format("%,d원", price);
    }

    @Transactional
    public void removeFromWishlist(int indexId) {
        // 위시리스트에서 제거
        wishlistRepository.deleteById(indexId);

        // 메인 화면에서 좋아요 상태 업데이트 (좋아요 취소)
        productRepository.findById(indexId).ifPresent(product -> {
            product.setLikeState(false);
            productRepository.save(product);
        });
    }
}
