package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

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
            List<Product> products = searchService.searchProducts(productName, productPrice, registrationDate);
            model.addAttribute("products", products);
        }
        return "search/search";

    }
}
