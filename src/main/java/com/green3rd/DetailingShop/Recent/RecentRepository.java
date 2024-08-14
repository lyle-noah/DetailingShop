package com.green3rd.DetailingShop.Recent;

import com.green3rd.DetailingShop.ProductList.Product; // Product 클래스 import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecentRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p ORDER BY p.registrationDate DESC")
    Page<Product> findRecentProducts(Pageable pageable);
}
