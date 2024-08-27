package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    Optional<Cart> findByUserAndProduct(User user, Product product);
    List<Cart> findByUserAndCartStateTrue(User user); // cartState가 true인 항목만 가져옴
}
