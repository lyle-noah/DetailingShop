package com.green3rd.DetailingShop.UserLike;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikesRepository extends JpaRepository<UserLikes, Long> {
    Optional<UserLikes> findByUserAndProduct(User user, Product product);

    // 특정 상품의 like_state가 1인 레코드 수를 반환
    int countByProductIndexIdAndLikeState(int productIndexId, boolean likeState);

    // 사용자가 좋아요한 상품 목록을 가져오는 메소드
    /*@Query("SELECT p FROM Product p WHERE p.id IN (SELECT ul.productId FROM UserLikes ul WHERE ul.userId = :userId AND ul.likeState = 1)")
    List<Product> findLikedProductsByUserId(@Param("userId") Long userId);*/
}
