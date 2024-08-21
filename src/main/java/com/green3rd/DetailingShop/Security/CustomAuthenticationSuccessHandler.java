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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    // RequestCache를 사용하여 사용자가 요청한 URL을 저장합니다.
    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // redirectUrl, targetUrl, SavedRequest를 처리
        String redirectUrl = request.getParameter("redirectUrl");
        String targetUrl = request.getParameter("targetUrl");
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        // 로그로 redirectUrl 출력하여 확인
        System.out.println("Redirecting to redirectUrl: " + redirectUrl);

        if (redirectUrl != null && !redirectUrl.isEmpty()) {
            // redirectUrl이 존재하면 우선적으로 처리
            String encodedRedirectUrl = encodeRedirectUrl(redirectUrl);
            logger.info("Redirecting to encoded redirectUrl: " + encodedRedirectUrl);
            response.sendRedirect(encodedRedirectUrl);
        } else if (savedRequest != null) {
            // SavedRequest가 존재하면 해당 URL로 리다이렉트
            String savedRequestUrl = savedRequest.getRedirectUrl();
            logger.info("Redirecting to SavedRequest URL: " + savedRequestUrl);
            response.sendRedirect(savedRequestUrl);
        } else if (targetUrl != null && !targetUrl.isEmpty()) {
            // targetUrl이 존재하면 해당 URL로 리다이렉트
            logger.info("Redirecting to targetUrl: " + targetUrl);
            response.sendRedirect(targetUrl);
        } else {
            // 아무런 URL도 없으면 기본 URL로 리다이렉트
            logger.info("Redirecting to default URL: /");
            response.sendRedirect("/");
        }
    }

    private String encodeRedirectUrl(String url) throws UnsupportedEncodingException {
        String baseUrl = url.split("\\?")[0];  // /products 부분
        String query = url.split("\\?").length > 1 ? url.split("\\?")[1] : "";  // 쿼리 파라미터 부분

        if (!query.isEmpty()) {
            String[] params = query.split("&");
            StringBuilder encodedParams = new StringBuilder();

            for (String param : params) {
                String[] keyValue = param.split("=");
                String key = keyValue[0];
                String value = keyValue[1];

                // value가 이미 인코딩된 경우 이를 다시 디코딩하여 중복 인코딩 방지
                value = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());

                // 다시 인코딩
                value = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());

                encodedParams.append(key).append("=").append(value).append("&");
            }

            if (encodedParams.length() > 0) {
                encodedParams.deleteCharAt(encodedParams.length() - 1);  // 마지막 & 제거
            }

            return baseUrl + "?" + encodedParams.toString();
        }
        return baseUrl;
    }
}