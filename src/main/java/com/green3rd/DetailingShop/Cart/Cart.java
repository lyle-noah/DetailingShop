package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.ProductList.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<Product> products;
}