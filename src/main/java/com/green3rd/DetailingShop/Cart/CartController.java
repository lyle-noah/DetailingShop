package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    // 장바구니 페이지 접근
    @GetMapping("/cart")
    public String getCartPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login";  // 로그인되지 않은 사용자는 로그인 페이지로 리다이렉트
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        // 장바구니와 총 금액을 모델에 추가
        model.addAttribute("cartItems", cartService.getCartByUser(user));
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(user));

        return "cart/cart";
    }

    // 장바구니에 상품 추가 기능
    @PostMapping("/cart/add/{indexId}")
    public String addToCart(@PathVariable int indexId,
                            @RequestParam("cartCount") int cartCount,
                            @RequestParam("redirectUrl") String redirectUrl,
                            Principal principal,
                            Model model) {

        if (principal == null) {
            // 비로그인 상태일 경우 로그인 페이지로 리다이렉트
            model.addAttribute("message", "로그인이 필요합니다.");
            model.addAttribute("urlYes", "/user/login?redirectUrl=" + redirectUrl);
            model.addAttribute("urlNo", redirectUrl);
            return "alert/alertMessage_form01";
        }

        // 로그인한 경우 장바구니에 추가 처리 로직
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            Cart cart = cartService.getOrCreateCart(user, product);

            // 장바구니에 상품이 이미 있는지 확인
            if (cart.isCartState()) { // 기존의 getCartState 메서드 대신 cart.getCartState() 사용
                // 상품이 이미 장바구니에 존재할 때 모달창 보여줌
                return "forms/modal_form02";
            }

            // 새로운 상품 장바구니 추가 로직
            cart.setCartState(true);
            cart.setCartCount(cartCount);
            cartService.saveCart(cart);
        }

        // 장바구니에 추가되면 모달창 띄우기
        return "forms/modal_form01";
    }

    // 장바구니에서 상품 수량 변경
    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Integer indexId,
                             @RequestParam("cartCount") int carCount,
                             Principal principal,
                             Model model) {

        User user = userService.findByUsername(principal.getName());
        cartService.updateCartCount(user, indexId, carCount);

        return "redirect:/cart";  // 장바구니 페이지로 리다이렉트
    }

    // 장바구니에서 상품 제거
    @PostMapping("/cart/delete")
    public String removeFromCart(@RequestParam("productId") Integer indexId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        cartService.removeFromCart(user, indexId);
        return "redirect:/cart";
    }
}