package com.green3rd.DetailingShop.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JavaMailSender mailSender;
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
            userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
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
        SiteUser user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("username", user.getUsername());
        return "profile";
    }

    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/user/login";
        }

        String username = authentication.getName();
        logger.info("Authenticated username: {}", username);
        SiteUser user = userService.getUser(username);
        logger.info("Retrieved user: {}", user.getUsername());
        model.addAttribute("user", user);
        return "header/mypage"; // 수정: 경로가 맞는지 확인
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("userResetPasswordForm", new UserResetPasswordForm());
        return "login/password_reset_request_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(@Valid UserResetPasswordForm userResetPasswordForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/password_reset_request_form";
        }

        String email = userResetPasswordForm.getEmail();
        String token = userService.createPasswordResetToken(email);
        if (token == null) {
            bindingResult.rejectValue("email", "emailNotFound", "등록된 이메일이 아닙니다.");
            return "login/password_reset_request_form";
        }

        String resetLink = "http://localhost:8080/user/reset_password_confirm?token=" + token;
        try {
            sendResetPasswordEmail(email, resetLink);
            logger.info("비밀번호 재설정 링크 전송 성공: {}", email);
        } catch (MessagingException e) {
            logger.error("비밀번호 재설정 링크 전송 실패: {}", email, e);
            bindingResult.reject("emailSendFailed", "이메일 전송에 실패했습니다.");
            return "login/password_reset_request_form";
        }

        return "redirect:/user/login";
    }

    private void sendResetPasswordEmail(String email, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("비밀번호 재설정 요청");
        helper.setText("비밀번호 재설정을 위해 다음 링크를 클릭하세요: <a href=\"" + resetLink + "\">비밀번호 재설정</a>", true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password_confirm")
    public String showResetPasswordConfirmForm(String token, Model model) {
        UserResetPasswordForm form = new UserResetPasswordForm();
        form.setToken(token);
        model.addAttribute("userResetPasswordForm", form);
        return "login/password_reset_form";
    }

    @PostMapping("/reset_password_confirm")
    public String resetPasswordConfirm(@Valid UserResetPasswordForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/password_reset_form";
        }

        if (!form.getPassword1().equals(form.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "login/password_reset_form";
        }

        boolean result = userService.resetPassword(form.getToken(), form.getPassword1());
        if (!result) {
            bindingResult.reject("resetFailed", "비밀번호 재설정에 실패했습니다.");
            return "login/password_reset_form";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/testpage")
    public String siteuser(Model model){
        List<SiteUser> siteusersInfo = userRepository.findAll();
        if (siteusersInfo.size() > 2) {
            String username = siteusersInfo.get(2).getUsername();
            model.addAttribute("username", username);
            System.out.println(username); // 확인용 출력
        } else {
            model.addAttribute("username", "No user found at index 2");
        }
        return "header/testpage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/user/login";
    }

    @Autowired
    private HttpServletRequest request;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("requestURI", request.getRequestURI());
    }
}
