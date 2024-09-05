package com.green3rd.DetailingShop.ProductList;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    private Integer indexId;
    private String productId;
    private String productName;
    private int productPrice;
    private String imgurl;
    private String firstCategory;

    // 생성자
    public ProductDto(Integer indexId, String productId, String productName, int productPrice, String imgurl, String firstCategory) {
        this.indexId = indexId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imgurl = imgurl;
        this.firstCategory = firstCategory;
    }
}
