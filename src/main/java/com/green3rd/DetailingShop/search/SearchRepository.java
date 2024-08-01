package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Product, Integer> {
    //상품 목록 검색
/*    List<Product> findByProduct_name(String product_name);*/
}
