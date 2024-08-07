package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import com.green3rd.DetailingShop.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    private final SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    //get 요청
    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String productName,
                                 @RequestParam(required = false) Integer productPrice,
                                 @RequestParam(required = false) LocalDateTime registrationDate,
                                 Model model) {
        // 빈 결과를 반환하도록 수정
        if ((productName == null || productName.isEmpty()) &&
                productPrice == null &&
                registrationDate == null) {
            model.addAttribute("products", Collections.emptyList());
        } else {
            // 검색 조건이 있을 시 searchService를 사용해 검색
            List<Product> products = searchService.searchProducts(productName, productPrice, registrationDate);
            model.addAttribute("products", products);
        }
        return "search/search";
    }
}
