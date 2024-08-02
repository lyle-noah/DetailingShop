package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class ProductSpecification {


    public static Specification<Product> hasProductName(String productName) {
        return (root, query, cb) -> cb.like(root.get("Product_name"), "%" + productName + "%");
    }

    public static Specification<Product> hasProductPrice(Integer productPrice) {
        return (root, query, cb) -> cb.equal(root.get("Product_price"), productPrice);
    }

    public static Specification<Product> hasRegistrationDate(LocalDateTime registrationDate) {
        return (root, query, cb) -> cb.equal(root.get("Registration_date"), registrationDate);
    }
}
