package com.green3rd.DetailingShop.ProductList;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProductsByCategory(String firstCategory, String secondCategory, String thirdCategory) {
        if (!secondCategory.isEmpty() && !thirdCategory.isEmpty()) {
            return productRepository.findByFirstCategoryAndSecondCategoryAndThirdCategory(firstCategory, secondCategory, thirdCategory);
        } else if (!secondCategory.isEmpty()) {
            return productRepository.findByFirstCategoryAndSecondCategory(firstCategory, secondCategory);
        } else if (!firstCategory.isEmpty()) {
            return productRepository.findByFirstCategory(firstCategory);
        }
        return List.of();
    }

    /* 제품 가격타입(ex.2000) -> (ex.2,000원) 변경 변수*/
    public String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return currencyFormat.format(price);
    }
}
