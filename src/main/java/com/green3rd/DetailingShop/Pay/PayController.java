package com.green3rd.DetailingShop.Pay;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PayController {

    @GetMapping("/pay")
    public String PayPage() {
        return "pay/pay";
    }

    @PostMapping("/process")
    public String process(String cardNumber, String cardExpiry, String cardCVC, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("message", "결제가 성공적으로 완료되었습니다.");
        return "redirect:/pay/success";
    }

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("message","결제가 성공적으로 완료되었습니다.");
        return "pay/success";
    }
}
