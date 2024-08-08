package com.green3rd.DetailingShop.ProductList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    // 카테고리에 따라 제품을 찾고, 페이지네이션을 적용할 수 있는 메서드 추가
    // Find products by the first category
    Page<Product> findByFirstCategory(String firstCategory, Pageable pageable);

    // Find products by first and second categories
    Page<Product> findByFirstCategoryAndSecondCategory(String firstCategory, String secondCategory, Pageable pageable);

    // Find products by first, second, and third categories
    Page<Product> findByFirstCategoryAndSecondCategoryAndThirdCategory(String firstCategory, String secondCategory, String thirdCategory, Pageable pageable);
}
