package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    //검색창 페이지
    @GetMapping("/search-page")
    public  String showSearchPage() {
        return  "search";
    }

    //검색
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return searchService.searchProductsByName(name);
    }
}
