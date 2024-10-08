package com.green3rd.DetailingShop.ProductList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    // 카테고리에 따라 제품을 찾고, 페이지네이션을 적용할 수 있는 메서드 추가
    // Find products by the first category
    Page<Product> findByFirstCategory(String firstCategory, Pageable pageable);

    // Find products by first and second categories
    Page<Product> findByFirstCategoryAndSecondCategory(String firstCategory, String secondCategory, Pageable pageable);

    // Find products by first, second, and third categories
    Page<Product> findByFirstCategoryAndSecondCategoryAndThirdCategory(String firstCategory, String secondCategory, String thirdCategory, Pageable pageable);

    // 검색페이지 페이지네이션 적용
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    Optional<Product> findByIndexId(int indexId);

    @Query("SELECT p FROM Product p LEFT JOIN p.likes l ON l.likeState = true WHERE p.firstCategory = :category GROUP BY p.indexId, p.productId, p.productName, p.productPrice, p.imgurl, p.cartState, p.likeState, p.firstCategory, p.secondCategory, p.thirdCategory, p.registrationDate ORDER BY COUNT(l.id) DESC, p.indexId ASC")
    List<Product> findTop4ByCategoryWithMostLikes(@Param("category") String category);

    // 관리자 페이지 상품아이디 문자열로 검색
    Optional<Product> findByProductId(String productId);

    // 관리자 페이지 상품 검색
    List<Product> findByProductNameContainingOrFirstCategoryContainingOrSecondCategoryContainingOrThirdCategoryContaining
    (String productName, String firstCategory, String secondCategory, String thirdCategory);
}
