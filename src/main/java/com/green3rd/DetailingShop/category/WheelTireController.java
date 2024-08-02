package com.green3rd.DetailingShop.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category/wheel_tire")
public class WheelTireController {

    @GetMapping
    public String getWheelTirePage() {
        return "category/wheel_tire";
    }
}
