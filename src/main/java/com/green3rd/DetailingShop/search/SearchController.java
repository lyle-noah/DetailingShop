package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String searchProducts(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
        List<Product> searchResults = searchService.searchProductsByName(query);
        model.addAttribute("Searchinfo", searchResults);
        return "search/searchResults"; // 검색 결과를 보여줄 뷰 이름
    }
}
