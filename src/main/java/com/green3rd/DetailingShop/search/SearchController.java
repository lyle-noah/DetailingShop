package com.green3rd.DetailingShop.search;

import com.green3rd.DetailingShop.product.Product;
import com.green3rd.DetailingShop.product.ProductRepository;
import com.green3rd.DetailingShop.user.SiteUser;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ProductRepository productRepository;

    //검색창 페이지
    @GetMapping("/search-page")
    public  String showSearchPage() {
        return  "search";
    }

    //검색
    @GetMapping("/search")
    @ResponseBody
        public  String searchbasket(Model model){
        List<Product> searchbasketInfo= productRepository.findAll();
        model.addAttribute("Searchinfo", searchbasketInfo);
        System.out.println(searchbasketInfo.get(2).getProduct_name());
        return "";
    }
}
