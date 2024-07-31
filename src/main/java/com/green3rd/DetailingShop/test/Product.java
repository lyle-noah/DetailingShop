package com.green3rd.DetailingShop.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int index_id;
    private String Product_id;
    private String Product_name;
    private int Product_price;
    private LocalDateTime Registration_date;
    private String first_category;
    private String second_category;
    private String third_category;
    private String imgURL;
}
