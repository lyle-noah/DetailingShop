package com.green3rd.DetailingShop.ProductList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    // Find products by the first category
    List<Product> findByFirstCategory(String firstCategory);

    // Find products by first and second categories
    List<Product> findByFirstCategoryAndSecondCategory(String firstCategory, String secondCategory);

    // Find products by first, second, and third categories
    List<Product> findByFirstCategoryAndSecondCategoryAndThirdCategory(String firstCategory, String secondCategory, String thirdCategory);
}
