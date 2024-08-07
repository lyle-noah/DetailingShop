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
    private String ProductId;

    private String ProductName;
    private int ProductPrice;
    private LocalDateTime RegistrationDate;
    private String firstCategory;
    private String secondCategory;
    private String thirdCategory;
    private String imgURL;

    @Transient
    private String formattedPrice; // 추가된 필드

}
