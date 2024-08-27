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

    // 상품 목록 조회
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products" , products);
        return "admin/products";
    }

    // 유저 목록 조회
    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users" , userService.findAllUsers());
        return "admin/users";
    }

    // 상품 추가 페이지
    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product" , new Product());
        return "admin/addProductForm";
    }

    // 상품 추가 처리
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.save(product);
        return "redirect:/admin/products";
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
