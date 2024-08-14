package com.green3rd.DetailingShop.Recent;

import com.green3rd.DetailingShop.ProductList.Product; // Product 클래스 import
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RecentService {

    private final RecentRepository recentRepository;

    public Page<Product> getRecentProducts(Pageable pageable) {
        return recentRepository.findRecentProducts(pageable);
    }

    public String formatPrice(int price) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return currencyFormat.format(price);
    }
}
