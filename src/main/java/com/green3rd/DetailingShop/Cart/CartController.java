package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.LoginUser.SiteUser;
import com.green3rd.DetailingShop.LoginUser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import com.green3rd.DetailingShop.ProductList.Product;

@Controller
public class CartController {
    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public CartController(UserRepository userRepository, CartService cartService) {
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    // 장바구니 페이지
    @GetMapping("/cart")
    public String CartPage(Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증되지 않았거나 로그인된 사용자가 없는 경우
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        }
        // 로그인된 사용자의 이름 가져오기
        String siteUsername = authentication.getName();
        SiteUser user = userRepository.findByUsername(siteUsername).orElse(null);
        if (user == null) {
            return "redirect:/login";  // 만약 사용자를 찾지 못한 경우 로그인 페이지로 리다이렉트
        }
        // 유저의 장바구니 가져오기
        Cart cart = cartService.getUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setProducts(new ArrayList<>());  // 빈 리스트로 초기화
        }
        // 모델에 장바구니와 총 가격을 추가하여 뷰로 전달
        model.addAttribute("cart", cart);
        int totalPrice = cart.getProducts().stream().mapToInt(Product::getProductPrice).sum();
        model.addAttribute("totalPrice", totalPrice);
        return "cart/cart";  // cart.html 템플릿을 반환
    }

    // 장바구니 추가
    @PostMapping("/cart/add")
    public String addCart(@RequestParam String productId) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String siteUsername = authentication.getName();
        SiteUser user = userRepository.findByUsername(siteUsername).orElse(null);
        if (user == null) {
            return "redirect:/login";  // 유저가 없으면 로그인 페이지로 리다이렉트
        }
        // 장바구니에 상품 추가
        cartService.addCart(user, productId);
        // 장바구니 페이지로 리다이렉트
        return "redirect:/cart";
    }
}