package com.green3rd.DetailingShop.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/user/forgot-password")).permitAll() // 비밀번호 재설정 경로 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // H2 콘솔 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/uploads/profileImages/**")).permitAll() // 프로필 이미지 경로에 대한 접근 허용
                        .requestMatchers(new AntPathRequestMatcher("/user/profile/uploadImage")).authenticated() // 프로필 이미지 업로드는 인증 필요
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // 전체 페이지 접근 허용
                        .anyRequest().authenticated()) // 그 외의 경로는 인증 필요

                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/user/logout"),
                                new AntPathRequestMatcher("/user/forgot-password"), // 비밀번호 재설정 경로에 대해 CSRF 비활성화
                                new AntPathRequestMatcher("/user/profile/uploadImage") // 프로필 이미지 업로드에 대해 CSRF 비활성화
                        ))

                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // 세션이 항상 생성되도록 설정

                .headers((headers) -> headers.addHeaderWriter(
                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))

                .formLogin((formLogin) -> formLogin
                        .loginPage("/user/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())) // Custom 성공 처리 핸들러 설정

                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")); // 메인 페이지로 리디렉션

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(); // 사용자 정의 성공 핸들러를 빈으로 등록
    }
}
