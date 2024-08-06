package com.green3rd.DetailingShop.cart;

import com.green3rd.DetailingShop.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(SiteUser siteUser);
}
