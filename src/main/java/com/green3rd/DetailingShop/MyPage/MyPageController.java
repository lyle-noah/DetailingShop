package com.green3rd.DetailingShop.MyPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model) {
        // 필요한 데이터를 모델에 추가
        return "mypage";
    }
}