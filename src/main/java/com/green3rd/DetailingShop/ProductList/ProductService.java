package com.green3rd.DetailingShop.ProductList;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import com.green3rd.DetailingShop.UserLike.UserLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 좋아요 버튼의 동작을 수집하고 DB에 저장.
    public boolean toggleLike(User user, Product product) {
        // 현재 사용자와 상품의 좋아요 상태를 검색
        UserLikes userLike = userLikesRepository.findByUserIdAndProductIndexId(user.getId(), product.getIndexId());

        if (userLike != null) {
            // 현재 상태를 반전시킴
            boolean newLikeState = !userLike.isLikeState();
//            System.out.println("Current like state: " + userLike.isLikeState()); // 현재 상태 로그 출력
//            System.out.println("New like state will be: " + newLikeState); // 반전된 상태 로그 출력
            userLike.setLikeState(newLikeState);
            userLikesRepository.save(userLike);  // 변경된 상태를 저장
            return newLikeState;
        } else {
            // 만약 사용자가 해당 상품에 대해 좋아요를 누른 적이 없다면 새로 생성
            UserLikes newUserLike = new UserLikes();
            newUserLike.setUser(user);
            newUserLike.setProduct(product);
            newUserLike.setLikeState(true);  // 기본적으로 좋아요로 설정
            userLikesRepository.save(newUserLike);  // 새 항목을 저장
            return true;
        }
    }

    // 사용자들이 누른 좋아요 수 세기.
    public int getLikesCountForProduct(int productIndexId) {
        return userLikesRepository.countByProductIndexIdAndLikeState(productIndexId, true);
    }

    // 관리자 페이지 상품 목록
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 관리자 페이지 상품 저장
    public void save(Product product) {
        productRepository.save(product);
    }

    // 관리자 페이지 상품 삭제
    public void deleteProductById(Integer id) {
        productRepository.deleteById(id);
    }

    // 관리자 페이지 indexId로 검색 (기본 키를 이용한 검색)
    public Product findProductByIndexId(Integer indexId) {
        return productRepository.findById(indexId).orElse(null);
    }

    // 관리자 페이지 productId로 검색 (문자열 ID를 이용한 검색)
    public Product findProductById(String productId) {
        return productRepository.findByProductId(productId).orElse(null);
    }

    // 관리자 페이지에서 상품 검색
    public List<Product> searchProductsByKeyword(String keyword) {
        return productRepository.findByProductNameContainingOrFirstCategoryContainingOrSecondCategoryContainingOrThirdCategoryContaining
                (keyword, keyword, keyword, keyword);
    }

}