package com.green3rd.DetailingShop.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    public final ProductRepository productRepository;

    @GetMapping("/categorypage")
    public String productlist(Model model) {
        List<Product> productInfo = productRepository.findAll();
        model.addAttribute("products", productInfo);
        return "forms/categorylist";
    }
}
