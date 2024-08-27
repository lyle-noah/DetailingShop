package com.green3rd.DetailingShop.Pay;

import com.green3rd.DetailingShop.Cart.CartService;
import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class PayController {

    private final IamportClient iamportClient;
    private final UserRepository userRepository;
    private final CartService cartService;

    @Autowired
    public PayController(@Value("${iamport.api.key}") String apiKey,
                             @Value("${iamport.api.secret}") String apiSecret,
                             UserRepository userRepository,
                             CartService cartService) {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
        this.userRepository = userRepository;
        this.cartService = cartService;
    }

    @PostMapping("/pay/complete")
    public String paymentComplete(@RequestParam Map<String, String> params, Model model) {
        String impUid = params.get("imp_uid");
        String merchantUid = params.get("merchant_uid");

        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);

            if (paymentResponse != null && paymentResponse.getResponse() != null) {
                Payment payment = paymentResponse.getResponse();
                // 결제 성공 시 처리 로직
                if (payment.getStatus().equals("paid")) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    String username = authentication.getName();
                    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

                    // 결제 후 장바구니 비우기
                    // cartService.clearCart(user);

                    model.addAttribute("message", "결제가 완료되었습니다.");
                    model.addAttribute("payment", payment);
                    return "pay/success";
                } else {
                    model.addAttribute("message", "결제에 실패하였습니다.");
                    return "pay/fail";
                }
            }
        } catch (Exception e) {
            model.addAttribute("message", "결제 처리 중 오류가 발생하였습니다.");
        }

        return "pay/fail";
    }
}
