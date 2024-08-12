package com.green3rd.DetailingShop.ProductList;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

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

    /* 제품 가격타입(ex.2000) -> (ex.2,000원) 변경 변수*/
    public String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return currencyFormat.format(price);
    }
    @Transactional
    public void toggleLike(int indexId) {
        Product product = productRepository.findById(indexId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid indexId Id:" + indexId));
        product.setLikeState(!product.isLikeState());
        productRepository.save(product);
    }
}
