package com.green3rd.DetailingShop.UserLike;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikesRepository extends JpaRepository<UserLikes, Long> {
    Optional<UserLikes> findByUserAndProduct(User user, Product product);

    // 특정 상품의 like_state가 1인 레코드 수를 반환
    int countByProductIndexIdAndLikeState(int productIndexId, boolean likeState);
}
