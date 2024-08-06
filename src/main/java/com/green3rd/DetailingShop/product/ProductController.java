package com.green3rd.DetailingShop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/외부관리")
    public String getProductExterior(Model model) {
        List<Product> products = productService.getProductsByCategory("외부관리");
        model.addAttribute("productsInfor", products);
        return "category/exterior";
    }

    @GetMapping("/내부관리")
    public String getProductInterior(Model model) {
        List<Product> products = productService.getProductsByCategory("내부관리");
        model.addAttribute("productsInfor", products);
        return "category/interior";
    }

    @GetMapping("/휠&타이어")
    public String getProductWheelTire(Model model) {
        List<Product> products = productService.getProductsByCategory("휠&타이어");
        model.addAttribute("productsInfor", products);
        return "category/wheel_tire";
    }

    @GetMapping("/타월&도구")
    public String getProductTowelTool(Model model) {
        List<Product> products = productService.getProductsByCategory("타월&도구");
        model.addAttribute("productsInfor", products);
        return "category/towel_tool";
    }

    @GetMapping("/광택폴리싱")
    public String getProductGlossyPolicing(Model model) {
        List<Product> products = productService.getProductsByCategory("광택 폴리싱");
        model.addAttribute("productsInfor", products);
        return "category/glossy_policing";
    }
}