package com.green3rd.DetailingShop.Admin;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String Home() {
        return "admin/admin";
    }

    // 상품 목록 조회
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products" , products);
        model.addAttribute("view" , "products");
        return "admin/admin";
    }

    // 유저 목록 조회
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users" , userService.findAllUsers());
        model.addAttribute("view" , "users");
        return "admin/admin";
    }

//    // 상품 추가 페이지
//    @GetMapping("/products/add")
//    public String addProductForm(Model model) {
//        model.addAttribute("product" , new Product());
//        model.addAttribute("view" , "addProductForm");
//        return "admin/admin";
//    }

    // 상품 추가 폼을 보여주는 메서드
    @GetMapping("/products/add")
    public String addProductForm(Model model) {
    model.addAttribute("product", new Product()); // 빈 Product 객체를 모델에 추가
    return "admin/addProductForm"; // addProductForm.html 템플릿 반환
    }

//    // 상품 추가 처리
//    @PostMapping("/products/add")
//    public String addProduct(@ModelAttribute Product product) {
//        productService.save(product);
//        return "redirect:/admin/products";
//    }

    // 상품 추가를 처리하는 메서드
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        // 제품 저장 로직 (예: productService.save(product))
        return "redirect:/admin/products"; // 저장 후 제품 목록 페이지로 리다이렉트
    }

    // 상품 삭제 처리
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    // 유저 삭제 처리
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteByUserId(id);
        return "redirect:/admin/users";
    }
}
