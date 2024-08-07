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
        //시작=null
        Specification<Product> spec = Specification.where(null);

        //제품 이름 검색 시 spec에 추가
        if (productName != null && !productName.isEmpty()) {
            spec = spec.and(ProductSpecification.hasProductName(productName));
        }

        //제품 가격 검색 시 spec에 추가
        if (productPrice != null) {
            spec = spec.and(ProductSpecification.hasProductPrice(productPrice));
        }

        //제품 등록일 검색 시 spec에 추가
        if (registrationDate != null) {
            spec = spec.and(ProductSpecification.hasRegistrationDate(registrationDate));
        }

        //총합 스펙으로 검색
        return productRepository.findAll(spec);
    }
}
