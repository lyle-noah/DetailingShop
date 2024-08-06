package com.green3rd.DetailingShop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsByCategory() {
        return productRepository.findAllByOrderByFirstCategoryAscSecondCategoryAscThirdCategoryAsc();
    }
}
