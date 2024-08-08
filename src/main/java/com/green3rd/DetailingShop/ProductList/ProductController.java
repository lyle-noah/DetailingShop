package com.green3rd.DetailingShop.ProductList;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String getProductByCategory(
            @RequestParam String firstCategory,
            @RequestParam(required = false, defaultValue = "") String secondCategory,
            @RequestParam(required = false, defaultValue = "") String thirdCategory,
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 (기본값: 0)
            @RequestParam(defaultValue = "10") int size,  // 페이지 크기 (기본값: 10)
            Model model) {

        Page<Product> productPage = productService.getProductsByCategory(firstCategory, secondCategory, thirdCategory, page, size);
        productPage.forEach(product -> product.setFormattedPrice(productService.formatPrice(product.getProductPrice())));

        // 제품의 페이지 데이터를 모델에 추가
        model.addAttribute("productsInfor", productPage.getContent());
        model.addAttribute("firstCategoryName", firstCategory);
        model.addAttribute("secondCategoryName", secondCategory);
        model.addAttribute("thirdCategoryName", thirdCategory);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());

        if (!thirdCategory.isEmpty()) {
            return "category/thirdCategory";
        } else if (!secondCategory.isEmpty()) {
            return "category/secondCategory";
        } else {
            return "category/firstCategory";
        }
    }
}