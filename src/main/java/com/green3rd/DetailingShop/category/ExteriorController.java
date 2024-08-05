package com.green3rd.DetailingShop.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/exterior")
public class ExteriorController {

    @GetMapping
    public String getExteriorPage() {
        return "category/exterior";
    }
}
