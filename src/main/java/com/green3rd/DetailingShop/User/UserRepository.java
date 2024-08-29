package com.green3rd.DetailingShop.User;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // 관리자 페이지 유저 검색
    // ID, username, 이메일 중 하나라도 일치하면 결과에 포함
    List<User> findByIdOrUsernameContainingOrEmailContaining(Long id, String username, String email);

    // username 또는 이메일 중 하나라도 일치하면 결과에 포함
    List<User> findByUsernameContainingOrEmailContaining(String username, String email);
}
