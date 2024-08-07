package com.green3rd.DetailingShop.ProductList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByFirstCategory(category);
    }
}
