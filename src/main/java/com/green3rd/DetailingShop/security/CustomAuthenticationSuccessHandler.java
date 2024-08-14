package com.green3rd.DetailingShop.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // targetUrl 파라미터 확인
        String targetUrl = request.getParameter("targetUrl");

        if (targetUrl != null && !targetUrl.isEmpty()) {
            // targetUrl이 존재하면 해당 URL로 리다이렉트
            response.sendRedirect(targetUrl);
        }

        // targetUrl이 없으면 세션에 저장된 URL을 가져옴
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
            response.sendRedirect(targetUrl);
            return;
        }

        // 기본적으로 메인 페이지로 리다이렉트
        response.sendRedirect("/");
    }
}
