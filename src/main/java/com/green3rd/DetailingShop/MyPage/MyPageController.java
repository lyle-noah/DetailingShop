package com.green3rd.DetailingShop.MyPage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

    @GetMapping("/mypage")
    public String myPage(Model model) {
        // 필요한 데이터를 모델에 추가
        return "header/mypage"; // 템플릿 파일이 header 디렉토리에 있는 경우
    }
}
