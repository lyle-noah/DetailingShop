package com.green3rd.DetailingShop.ProductList;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserRepository;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import com.green3rd.DetailingShop.UserLike.UserLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserLikesRepository userLikesRepository;

    public Page<Product> getProductsByCategory(String firstCategory, String secondCategory, String thirdCategory, int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 페이지네이션을 위한 Pageable 객체 생성

        if (!secondCategory.isEmpty() && !thirdCategory.isEmpty()) {
            return productRepository.findByFirstCategoryAndSecondCategoryAndThirdCategory(firstCategory, secondCategory, thirdCategory, pageable);
        } else if (!secondCategory.isEmpty()) {
            return productRepository.findByFirstCategoryAndSecondCategory(firstCategory, secondCategory, pageable);
        } else if (!firstCategory.isEmpty()) {
            return productRepository.findByFirstCategory(firstCategory, pageable);
        }
        return Page.empty();
    }

    /* 제품 가격타입(ex.2000) -> (ex.2,000원) 변경 변수 */
    public String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return currencyFormat.format(price);
    }

    // 상품 ID 상세 정보 조회
    public Product getProductByID(int indexId) {
        return  productRepository.findById(indexId).orElse(null);
    }

    // 좋아요 상태를인식하고 이를 DB에 실시간으로 저장해줌.
    public Product findByIndexId(int indexId) {
        return productRepository.findByIndexId(indexId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Index Id:" + indexId));
    }

    public void toggleLike(User user, Product product) {
        Optional<UserLikes> userLikesOptional = userLikesRepository.findByUserAndProduct(user, product);

        if (userLikesOptional.isPresent()) {
            UserLikes userLikes = userLikesOptional.get();
            userLikes.setLikeState(!userLikes.isLikeState());
            userLikesRepository.save(userLikes);
        } else {
            UserLikes newUserLike = new UserLikes();
            newUserLike.setUser(user);
            newUserLike.setProduct(product);
            newUserLike.setLikeState(true);
            userLikesRepository.save(newUserLike);
        }
    }
}