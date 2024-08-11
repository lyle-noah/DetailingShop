package com.green3rd.DetailingShop.ProductSearch;

import com.green3rd.DetailingShop.ProductList.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String searchProducts(@RequestParam("productName") String productName,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = searchService.searchProducts(productName, pageable);

        if (productsPage.isEmpty()) {
            // 검색 결과가 없을 경우
            return "search/searchNoResult"; // 상품이 없다는 페이지로 리다이렉트
        }

        int totalPages = productsPage.getTotalPages();
        int currentPage = pageable.getPageNumber();
        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, currentPage + 2);

        // 상품 수량 정보
        int totalProducts = (int)productsPage.getTotalElements();

        // 검색제품의 데이터를 모델에 전달
        model.addAttribute("searchResult", productsPage.getContent());
        model.addAttribute("query", productName);

        // 제품 페이지 정보와 연산 정보값을 모델에 전달
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 검색 제품의 수량 데이터를 모델에 전달
        model.addAttribute("totalProducts", totalProducts);

        return "search/searchResult";
    }
}