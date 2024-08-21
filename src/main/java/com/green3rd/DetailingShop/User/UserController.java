package com.green3rd.DetailingShop.User;

import java.util.List;

import com.green3rd.DetailingShop.Security.UserNotFoundException;
import com.green3rd.DetailingShop.UserCreate.UserCreateForm;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "login/signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/signup_form";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "login/signup_form";
        }

        try {
            userService.create(
                    userCreateForm.getUsername(),
                    userCreateForm.getPassword1(),
                    userCreateForm.getEmail(),
                    userCreateForm.getSecurityQuestion(),
                    userCreateForm.getSecurityAnswer()
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "login/signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "login/signup_form";
        }
        return "redirect:/user/mypage";
    }


    @GetMapping("/login")
    public String login() {
        return "login/login_form";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login";
        }

        String username = authentication.getName();
        User user;
        try {
            user = userService.getUser(username);
        } catch (UserNotFoundException e) {
            return "redirect:/user/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        return "profile";
    }

    @GetMapping("/mypage")
    public String myPage(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // 로그인이 필요한 경우, 로그인 페이지로 리다이렉트하면서 targetUrl 파라미터에 원래 요청 URL을 포함
            return "redirect:/user/login?targetUrl=" + request.getRequestURI();
        }

        String username = authentication.getName();
        logger.info("Authenticated username: {}", username);
        User user;
        try {
            user = userService.getUser(username);
        } catch (UserNotFoundException e) {
            return "redirect:/user/login";
        }
        logger.info("Retrieved user: {}", user.getUsername());
        model.addAttribute("user", user);
        return "mypage/mypage";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordForm(@RequestParam(required = false) String username, Model model) {
        if (username != null) {
            try {
                User user = userService.getUser(username);
                model.addAttribute("securityQuestion", user.getSecurityQuestion());
                model.addAttribute("username", username);
            } catch (UserNotFoundException e) {
                // 여기에서 에러 메시지를 모델에 추가
                model.addAttribute("error", "등록되어 있지 않은 아이디입니다.");
            }
        }
        return "login/forgot_password_form";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String username,
                                 @RequestParam String securityAnswer,
                                 @RequestParam String newPassword,
                                 Model model) {
        if (userService.checkSecurityAnswer(username, securityAnswer)) {
            userService.resetPassword(username, newPassword);
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "올바르지 않은 답입니다.");
            model.addAttribute("securityQuestion", userService.getUser(username).getSecurityQuestion());
            model.addAttribute("username", username);
            return "login/forgot_password_form";
        }
    }

    @GetMapping("/testpage")
    public String siteuser(Model model) {
        List<User> siteusersInfo = userRepository.findAll();
        if (siteusersInfo.size() > 2) {
            String username = siteusersInfo.get(2).getUsername();
            model.addAttribute("username", username);
            System.out.println(username); // 확인용 출력
        } else {
            model.addAttribute("username", "No user found at index 2");
        }
        return "header/testpage";
    }

    @Autowired
    private HttpServletRequest request;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
    }
}