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
    @ResponseBody
    public String addToCart(@PathVariable int indexId,
                            @RequestParam("cartCount") int cartCount,
                            @RequestParam("redirectUrl") String redirectUrl,
                            Principal principal,
                            Model model) {

        if (principal == null) {
            return "alert/alertMessage_form01";
        }

        // 로그인한 경우 장바구니에 추가 처리 로직
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            Cart cart = cartService.getOrCreateCart(user, product);

            if (cart.isCartState()) {
                return "forms/modal_form02";
            }

            cart.setCartState(true);
            cart.setCartCount(cartCount);
            cartService.saveCart(cart);
        }

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