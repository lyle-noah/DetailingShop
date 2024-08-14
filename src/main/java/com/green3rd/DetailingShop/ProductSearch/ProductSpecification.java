package com.green3rd.DetailingShop.ProductSearch;

import com.green3rd.DetailingShop.ProductList.Product;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

//검색 조건 정의
public class ProductSpecification {

    public static Specification<Product> containsTextInProductFields(String text) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + text.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), likePattern)
            );
        };
    }
}