package com.green3rd.DetailingShop.Cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.LoginUser.SiteUser;

import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저와 카트 1:1
    @OneToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private SiteUser user;

    // 카트와 상품 1:N
    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<Product> products;
}