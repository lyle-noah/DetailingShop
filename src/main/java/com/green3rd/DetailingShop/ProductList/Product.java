package com.green3rd.DetailingShop.ProductList;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "all_product")
@ToString
@Getter
@Setter
public class Product {

    @Id
    private int indexId;

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

    private boolean likeState = false;

    //  수량 필드
    private int quantity;
}
