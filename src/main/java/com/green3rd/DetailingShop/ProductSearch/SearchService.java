package com.green3rd.DetailingShop.ProductSearch;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;

    public Page<Product> searchProducts(String query, Pageable pageable) {
        return productRepository.findAll(ProductSpecification.containsTextInProductFields(query), pageable);
    }

    /* 제품 가격타입(ex.2000) -> (ex.2,000원) 변경 변수*/
    public String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return currencyFormat.format(price);
    }
}
