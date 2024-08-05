package com.green3rd.DetailingShop.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//@RequiredArgsConstructor 오류로 직접 생성자 주입
@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/categorypage")
    public String productlist(Model model) {
        List<Product> productInfo = productRepository.findAll();
        model.addAttribute("products", productInfo);
        return "forms/autoSlider_form";
    }
}
