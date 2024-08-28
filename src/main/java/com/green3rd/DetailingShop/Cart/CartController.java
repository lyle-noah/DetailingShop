package com.green3rd.DetailingShop.Cart;

import com.green3rd.DetailingShop.ProductList.Product;
import com.green3rd.DetailingShop.ProductList.ProductService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;


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

        // 비로그인 상태 처리
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            String redirectUrl = "/cart";
            return "redirect:/user/login?redirectUrl=" + redirectUrl;
        }

        // 로그인된 사용자 정보 가져오기
        Object principal = authentication.getPrincipal();
        User user;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            user = userService.findByUsername(username);
        } else {
            return "redirect:/user/login";
        }

        // 장바구니 목록 가져오기
        List<Cart> cartItems = cartService.getCartByUser(user);

        // 장바구니가 비어있는지 확인
        boolean isCartEmpty = cartItems.isEmpty();

        // 장바구니와 총 금액을 모델에 추가
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(user));
        model.addAttribute("isCartEmpty", isCartEmpty);

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

    // 장바구니 품목 삭제 (비동기 처리)
    @PostMapping("/cart/delete")
    public String deleteCartItem(@RequestParam("indexId") Integer indexId, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            cartService.deleteCartItem(user, product);
        }

        return "redirect:/cart";
    }

    // 장바구니 수량 업데이트 (비동기 처리)
    @PostMapping("/cart/update")
    public ResponseEntity<Void> updateCartItem(@RequestParam("indexId") Integer indexId, @RequestParam("cartCount") int cartCount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            cartService.updateCartItem(user, product, cartCount);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 장바구니 수량 변경 기능
    @PostMapping("/cart/update-quantity")
    public String updateCartQuantity(@RequestParam("indexId") Integer indexId, @RequestParam("cartCount") int cartCount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Product product = productService.findByIndexId(indexId);

        if (user != null && product != null) {
            cartService.updateCartItem(user, product, cartCount);
        }

        return "redirect:/cart";
    }


}