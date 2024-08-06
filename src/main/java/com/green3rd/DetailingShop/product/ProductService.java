package com.green3rd.DetailingShop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByFirstCategoryAscSecondCategoryAscThirdCategoryAsc();
    }
}
