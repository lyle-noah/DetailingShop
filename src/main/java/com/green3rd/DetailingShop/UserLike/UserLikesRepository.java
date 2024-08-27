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
public interface UserLikesRepository extends JpaRepository<UserLikes, Integer> {
    Optional<UserLikes> findByUserAndProduct(User user, Product product);

    // 특정 상품의 like_state가 1인 레코드 수를 반환
    int countByProductIndexIdAndLikeState(int productIndexId, boolean likeState);

    // 특정 사용자 ID와 likeState가 true인 경우에 해당하는 Product 목록을 반환
    @Query("SELECT ul.product FROM UserLikes ul WHERE ul.user.id = :userId AND ul.likeState = true")
    List<Product> findLikedProductsByUserId(@Param("userId") Integer userId);

    // 사용자 ID와 상품 IndexId로 UserLikes 찾기
    UserLikes findByUserIdAndProductIndexId(Integer userId, Integer indexId);
}
