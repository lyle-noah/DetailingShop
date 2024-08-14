package com.green3rd.DetailingShop.Recent;

import com.green3rd.DetailingShop.ProductList.Product; // Product 클래스 import
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession; // 세션을 사용하기 위한 import
import java.util.List;
import java.text.NumberFormat; // 추가된 import
import java.util.Locale; // 추가된 import

@Controller
@RequiredArgsConstructor
public class RecentController {

    @GetMapping("/recentview")
    public String getRecentView(Model model, HttpSession session) {

        // 세션에서 최근 본 상품 리스트를 가져옴
        List<Product> recentProducts = (List<Product>) session.getAttribute("recentProducts");

        if (recentProducts != null) {
            // 가격 포맷 설정
            recentProducts.forEach(product -> product.setFormattedPrice(
                    NumberFormat.getCurrencyInstance(Locale.KOREA).format(product.getProductPrice())));
        }

        // 모델에 데이터 설정
        model.addAttribute("productsInfor", recentProducts);

        return "mypage/recentview"; // 뷰 이름
    }

    @GetMapping("/orderhistory")
    public String orderHistory() {
        return "mypage/orderhistory";
    }
}
