package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import com.green3rd.DetailingShop.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProductsByName(String name) {
        // 데이터베이스에서 모든 상품을 가져와 메모리 내에서 필터링
        return productRepository.findAll().stream()
                .filter(product -> product.getProduct_name() != null && product.getProduct_name().contains(name))
                .collect(Collectors.toList());
    }
}
