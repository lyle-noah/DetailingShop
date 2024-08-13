package com.green3rd.DetailingShop.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CustomQueryParamRequestMatcher implements RequestMatcher {

    private final String paramName;
    private final String paramValue;

    public CustomQueryParamRequestMatcher(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        // 쿼리 매개변수의 값을 가져옴
        String value = request.getParameter(paramName);
        // 쿼리 매개변수가 존재하고, 그 값이 설정된 값과 일치하는지 확인
        return value != null && value.equals(paramValue);
    }
}
