package com.green3rd.DetailingShop.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.green3rd.DetailingShop.User.User;
import com.green3rd.DetailingShop.User.UserRepository;
import com.green3rd.DetailingShop.UserCreate.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> _siteUser = this.userRepository.findByUsername(username);
        if (_siteUser.isEmpty()) {
            log.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        User user = _siteUser.get();
        log.info("User found with username: {}", user.getUsername());
        log.info("User password: {}", user.getPassword());  // 추가된 로그
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        log.info("User authorities: {}", authorities);  // 추가된 로그
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}