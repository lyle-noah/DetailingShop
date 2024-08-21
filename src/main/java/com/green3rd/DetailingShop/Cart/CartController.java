package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;

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
            return "redirect:/user/login";
        }
        // 로그인된 사용자의 이름 가져오기
        String siteUsername = authentication.getName();
        User user = userRepository.findByUsername(siteUsername).orElse(null);
        if (user == null) {
            return "redirect:/user/login";
        }
        // 유저의 장바구니 가져오기
        Cart cart = cartService.getUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setProducts(new ArrayList<>());  // 빈 리스트로 초기화
        }

        double totalPrice = cartService.totalPrice(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart/cart";
    }

    // 장바구니 추가
    @PostMapping("/cart/add")
    public String addCart(@RequestParam String productId) {
        // 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String siteUsername = authentication.getName();
        User user = userRepository.findByUsername(siteUsername).orElse(null);
        if (user == null) {
            return "redirect:/user/login";
        }
        // 장바구니에 상품 추가
        cartService.addCart(user,productId);
        return "redirect:/cart";
    }

    // 장바구니 상품 수량 변경
    @PostMapping("/cart/update")
    public String updateCart(@RequestParam String productId, @RequestParam int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        User user = userRepository.findByUsername(Username).orElse(null);
        if (user == null) {
            return  "redirect:/user/login";
        }
        if (quantity <= 0) {
            // 유효하지 않은 수량 처리
            return "redirect:/cart?error=invalid_quantity";
        }
        cartService.updateCart(user, productId, quantity);
        return "redirect:/cart";
    }

    // 장바구니 상품 삭제
    @PostMapping("/cart/delete")
    public String deleteCart(@RequestParam String productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Username = authentication.getName();
        User user = userRepository.findByUsername(Username).orElse(null);
        if (user == null) {
            return "redirect:/user/login";
        }
        cartService.deleteCart(user, productId);
        return "redirect:/cart";
    }

}