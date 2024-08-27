package com.green3rd.DetailingShop.UserLike;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLikesService {

    private final UserLikesRepository userLikesRepository;

    public Optional<UserLikes> findByUserAndProduct(User user, Product product) {
        return userLikesRepository.findByUserAndProduct(user, product);
    }

    // 특정 사용자가 좋아요한 상품 목록을 가져오는 메서드
    public List<Product> getLikedProductsByUser(Integer userId) {
        return userLikesRepository.findLikedProductsByUserId(userId);
    }

    // 사용자의 likeState 업데이트 메서드
    public void updateLikeState(Integer userId, Integer indexId, boolean likeState) {
        UserLikes userLikes = userLikesRepository.findByUserIdAndProductIndexId(userId, indexId);
        if (userLikes != null) {
            userLikes.setLikeState(likeState); // boolean 값 설정
            userLikesRepository.save(userLikes);
        }
    }
}
