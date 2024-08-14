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

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl = request.getParameter("targetUrl");

        if (targetUrl == null) {
            logger.warn("targetUrl is null");
        } else {
            logger.info("Received targetUrl: " + targetUrl);
        }

        if (targetUrl != null && !targetUrl.isEmpty()) {
            logger.info("Redirecting to targetUrl: " + targetUrl);
            response.sendRedirect(targetUrl);
            return;
        }

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            targetUrl = savedRequest.getRedirectUrl();
            logger.info("Redirecting to SavedRequest URL: " + targetUrl);
            response.sendRedirect(targetUrl);
            return;
        }

        logger.info("Redirecting to default URL: /");
        response.sendRedirect("/");
    }
}