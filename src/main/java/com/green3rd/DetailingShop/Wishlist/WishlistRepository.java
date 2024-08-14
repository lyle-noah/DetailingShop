package com.green3rd.DetailingShop.Wishlist;

import com.green3rd.DetailingShop.ProductList.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Product, Integer> {

    // 좋아요가 눌린 상품만 가져오는 메서드
    Page<Product> findByLikeStateTrue(Pageable pageable);
}
