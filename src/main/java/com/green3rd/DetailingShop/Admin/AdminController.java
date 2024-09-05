package com.green3rd.DetailingShop.Admin;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import com.green3rd.DetailingShop.UserCreate.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String adminHome(Model model) {
        int totalUsers = userService.getTotalUsers();
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("view", "dashboard");
        return "admin/admin";
    }

    // 유저 목록 조회
    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("view", "users");
        return "admin/admin";
    }

    // 유저 추가 폼을 보여주는 메서드
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User()); // 빈 User 객체를 모델에 추가
        return "admin/addUserForm";
    }

    // 유저 추가를 처리하는 메서드
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user); // 유저 저장
        return "redirect:/admin/users"; // 저장 후 유저 목록 페이지로 리다이렉트
    }

    // 유저 수정 폼을 보여주는 메서드
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id); // 유저를 ID로 검색
        if (user == null) {
            return "redirect:/admin/users"; // 유저를 찾지 못한 경우 유저 목록 페이지로 리다이렉트
        }
        model.addAttribute("user", user);
        return "admin/editUserForm";
    }

    // 유저 수정 처리 메서드
    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        user.setId(id.intValue()); // ID를 설정
        userService.save(user); // 유저 저장 로직
        return "redirect:/admin/users";
    }

    // 유저 삭제 처리
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteByUserId(id);
        return "redirect:/admin/users";
    }

    // 유저 검색
    @GetMapping("/users/search")
    public String searchUsers(@RequestParam("keyword") String keyword, Model model) {
        List<User> users = userService.searchUsersByKeyword(keyword);
        model.addAttribute("users", users);
        model.addAttribute("view", "users");
        return "admin/admin";
    }

    // 상품 목록 조회
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("view", "products");
        return "admin/admin";
    }

    // 상품 추가 폼을 보여주는 메서드
    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product()); // 빈 Product 객체를 모델에 추가
        return "admin/addProductForm";
    }

    // 상품 편집 폼을 보여주는 메서드
    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Integer id, Model model) {
        Product product = productService.findProductByIndexId(id); // 상품을 ID로 검색
        if (product == null) {
            // 제품을 찾지 못한 경우
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "admin/editProduct";
    }

    // 상품 편집 처리 메서드
    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Integer id, @ModelAttribute("product") Product product) {
        product.setIndexId(id); // ID를 설정
        productService.save(product); // 제품 저장 로직
        return "redirect:/admin/products";
    }

    // 상품 추가를 처리하는 메서드
    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.save(product); // 제품 저장
        return "redirect:/admin/products";
    }

    // 상품 삭제 처리
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id); // indexId 기반 삭제
        return "redirect:/admin/products";
    }

    // 상품 검색
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = productService.searchProductsByKeyword(keyword);
        model.addAttribute("products", products);
        model.addAttribute("view", "products");
        return "admin/admin";
    }

}
