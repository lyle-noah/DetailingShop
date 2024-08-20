package com.green3rd.DetailingShop.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    // RequestCache를 사용하여 사용자가 요청한 URL을 저장합니다.
    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 로그인 페이지에서 전달된 targetUrl 파라미터를 가져옵니다.
        String targetUrl = request.getParameter("targetUrl");

        if (targetUrl == null) {
            logger.warn("targetUrl is null");
        } else {
            logger.info("Received targetUrl: " + targetUrl);
        }

        // targetUrl이 존재하고, 값이 비어 있지 않으면 해당 URL로 리다이렉트합니다.
        if (targetUrl != null && !targetUrl.isEmpty()) {
            logger.info("Redirecting to targetUrl: " + targetUrl);
            response.sendRedirect(targetUrl);
            return;
        }

        // targetUrl이 없으면, SavedRequest에서 리다이렉트할 URL을 가져옵니다.
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
            logger.info("Redirecting to SavedRequest URL: " + targetUrl);
            response.sendRedirect(targetUrl);
            return;
        }

        // SavedRequest도 없으면, 기본 URL인 "/"로 리다이렉트합니다.
        logger.info("Redirecting to default URL: /");
        response.sendRedirect("/");
    }
}