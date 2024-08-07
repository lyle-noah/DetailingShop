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
            Model model) {

        List<Product> products = productService.getProductsByCategory(firstCategory, secondCategory, thirdCategory);
        products.forEach(product -> product.setFormattedPrice(productService.formatPrice(product.getProductPrice())));
        model.addAttribute("productsInfor", products);
        model.addAttribute("firstCategoryName", firstCategory);
        model.addAttribute("secondCategoryName", secondCategory);
        model.addAttribute("thirdCategoryName", thirdCategory);

        if (!thirdCategory.isEmpty()) {
            return "category/thirdCategory";
        } else if (!secondCategory.isEmpty()) {
            return "category/secondCategory";
        } else {
            return "category/firstCategory";
        }
    }
}