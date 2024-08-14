package com.green3rd.DetailingShop.Wishlist;

import com.green3rd.DetailingShop.ProductList.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/wishlist")
    public String getWishlist(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> wishlistProducts = wishlistService.getWishlistProducts(pageable);

        wishlistProducts.forEach(product -> product.setFormattedPrice(wishlistService.formatPrice(product.getProductPrice())));

        List<Product> products = wishlistProducts.getContent();

        model.addAttribute("productsInfor", products);

        return "mypage/wishlist";
    }

    @PostMapping("/wishlist/remove/{indexId}")
    public String removeFromWishlist(@PathVariable int indexId, RedirectAttributes redirectAttributes) {
        wishlistService.removeFromWishlist(indexId);
        redirectAttributes.addFlashAttribute("message", "상품이 위시리스트에서 제거되었습니다.");
        return "redirect:/wishlist";
    }

}
