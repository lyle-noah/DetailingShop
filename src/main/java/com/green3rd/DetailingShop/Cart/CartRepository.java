package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long>{
    Optional<Cart> findByUser(User user);
}
