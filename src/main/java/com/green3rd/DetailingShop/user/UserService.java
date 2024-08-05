package com.green3rd.DetailingShop.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
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
                SiteUser user = resetToken.getUser();
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                passwordResetTokenRepository.delete(resetToken);
                return true;
            })
            .orElse(false);
    }
}
