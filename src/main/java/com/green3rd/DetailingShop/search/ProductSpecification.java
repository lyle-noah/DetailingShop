package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

//검색 조건 정의
public class ProductSpecification {

    //제품 이름 검색
    public static Specification<Product> hasProductName(String productName) {
        return (root, query, cb) -> cb.like(root.get("ProductName"), "%" + productName + "%");
    }

    //제품 가격 검색
    public static Specification<Product> hasProductPrice(Integer productPrice) {
        return (root, query, cb) -> cb.equal(root.get("ProductPrice"), productPrice);
    }

    //제품 등록일 검색
    public static Specification<Product> hasRegistrationDate(LocalDateTime registrationDate) {
        return (root, query, cb) -> cb.equal(root.get("RegistrationDate"), registrationDate);
    }
}
