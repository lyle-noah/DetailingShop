package com.green3rd.DetailingShop.User;

import com.green3rd.DetailingShop.Security.UserNotFoundException;
import com.green3rd.DetailingShop.UserCreate.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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

    // 관리자 페이지 유저 저장
    public void save(User user) {
        // 비밀번호 암호화 처리
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);
    }

    // 관리자 페이지 유저 ID로 찾기
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // 관리자 페이지 유저 검색
    public List<User> searchUsersByKeyword(String keyword) {
        try {
            // 키워드가 숫자인 경우, 이를 ID로 사용하여 검색
            Long id = Long.parseLong(keyword);
            return userRepository.findByIdOrUsernameContainingOrEmailContaining(id, keyword, keyword);
        } catch (NumberFormatException e) {
            // 키워드가 숫자가 아닌 경우, 부분 일치로 이름 또는 이메일 검색
            return userRepository.findByUsernameContainingOrEmailContaining(keyword, keyword);
        }
    }

    // 관리자 페이지 총 유저 수
    public int getTotalUsers() {
        return  (int) userRepository.count();
    }

    // 관리자 페이지 유저 로그인 시 현재 날짜로 업데이트
    public void updateLastLoginDate(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        user.setLastLoginDate(LocalDate.now());
        userRepository.save(user);
    }

//    // 관리자 페이지 기능 권한
//    public void updateUserRole(Long userId, UserRole role) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
//        user.setRole(role);
//        userRepository.save(user);
//    }

    public void updateProfileImage(String username, String imagePath) {
        User user = getUser(username);
        user.setProfileImagePath(imagePath);

        // 디버깅 출력
        System.out.println("Image path set for user: " + username + ", path: " + imagePath);

        userRepository.save(user);
    }

}
