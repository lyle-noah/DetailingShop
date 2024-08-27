package com.green3rd.DetailingShop.User;

import com.green3rd.DetailingShop.Security.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String username, String password, String email, String securityQuestion, String securityAnswer) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 비밀번호 해싱
        user.setEmail(email);
        user.setSecurityQuestion(securityQuestion);
        user.setSecurityAnswer(securityAnswer);
        this.userRepository.save(user);
        return user;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public boolean checkSecurityAnswer(String username, String answer) {
        User user = this.getUser(username);
        return user.getSecurityAnswer().equals(answer);
    }

    public void resetPassword(String username, String newPassword) {
        User user = this.getUser(username);
        user.setPassword(passwordEncoder.encode(newPassword)); // 비밀번호 해싱
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    // 관리자 페이지 유저 목록
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // 관리자 페이지 유저 삭제
    public void deleteByUserId(Long id) {
        userRepository.deleteById(id);
    }
}
