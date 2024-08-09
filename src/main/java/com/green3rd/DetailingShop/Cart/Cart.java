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

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private SiteUser user;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<Product> products;
}
