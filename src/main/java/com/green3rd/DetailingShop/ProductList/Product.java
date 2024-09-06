package com.green3rd.DetailingShop.ProductList;

import com.green3rd.DetailingShop.Cart.Cart;
import com.green3rd.DetailingShop.UserLike.UserLikes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "all_product")
@ToString
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer indexId;  // 이 컬럼이 all_product 테이블의 index_id와 매핑됩니다

    @Column(nullable = false)
    private String productId;

    private String productName;
    private int productPrice;
    private LocalDateTime registrationDate;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String imgurl;

    @Transient
    private String formattedPrice; // 추가된 필드

    // 좋아요 상태 엔티티 연결
    private boolean likeState = false;

    // 장바구니 상태 엔티티 연결
    private boolean cartState = false;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Cart> carts = new HashSet<>();

    // 좋아요버튼
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<UserLikes> likes = new HashSet<>();
}
