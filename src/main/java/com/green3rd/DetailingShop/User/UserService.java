package com.green3rd.DetailingShop.User;

import java.util.Optional;
import java.util.UUID;

import com.green3rd.DetailingShop.Security.UserNotFoundException;
import com.green3rd.DetailingShop.UserPassword.PasswordResetToken;
import com.green3rd.DetailingShop.UserPassword.PasswordResetTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public User create(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public String createPasswordResetToken(String email) {
        return userRepository.findByEmail(email)
            .map(siteUser -> {
                String token = UUID.randomUUID().toString();
                PasswordResetToken resetToken = new PasswordResetToken();
                resetToken.setToken(token);
                resetToken.setUser(siteUser);
                passwordResetTokenRepository.save(resetToken);
                return token;
            })
            .orElse(null);
    }

    public boolean resetPassword(String token, String newPassword) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token))
            .map(resetToken -> {
                User user = resetToken.getUser();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                passwordResetTokenRepository.delete(resetToken);
                return true;
            })
            .orElse(false);
    }
}