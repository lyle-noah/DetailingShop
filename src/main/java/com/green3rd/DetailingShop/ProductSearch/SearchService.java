package com.green3rd.DetailingShop.ProductSearch;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SearchService {

    private final ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProducts( String productName, Integer productPrice, LocalDateTime registrationDate) {
        Specification<Product> spec = Specification.where(null);


        if (productName != null && !productName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasProductName(productName));
        }

        if (productPrice != null) {
            spec = spec.and(ProductSpecification.hasProductPrice(productPrice));
        }

        if (registrationDate != null) {
            spec = spec.and(ProductSpecification.hasRegistrationDate(registrationDate));
        }

        return productRepository.findAll(spec);
    }
}
