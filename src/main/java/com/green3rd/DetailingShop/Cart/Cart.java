package com.green3rd.DetailingShop.Cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.User.User;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_index_id", nullable = false)
    private Product product;

    private boolean cartState;  // 장바구니 상태를 나타내는 boolean 필드
    private int cartCount;      // 장바구니 수량을 나타내는 int 필드

    // 기본 생성자
    public Cart() {
    }

    // User와 Product를 매개변수로 받는 생성자
    public Cart(User user, Product product) {
        this.user = user;
        this.product = product;
        this.cartCount = 0; // 기본값 설정
        this.cartState = false; // 기본값 설정
    }
}