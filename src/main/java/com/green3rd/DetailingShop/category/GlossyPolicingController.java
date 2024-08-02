package com.green3rd.DetailingShop.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/glossy_policing")
public class GlossyPolicingController {

    @GetMapping
    public String getGlossyPolicingPage() {
        return "category/glossy_policing";
    }
}