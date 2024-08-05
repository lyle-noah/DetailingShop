package com.green3rd.DetailingShop.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/towel_tool")
public class TowelToolController {

    @GetMapping
    public String getTowelToolPage() {
        return "category/towel_tool";
    }
}
