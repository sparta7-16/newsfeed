package com.example.newsfeed.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 회원가입, 로그인 요청은 인증 처리에서 제외
    private static final String[] WHITE_LIST = {"/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 다운캐스팅
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

//        log.info("로그인 필터 로직 실행");

        // WHITE_LIST에 포함되어 있지 않다면
        if (!isWhiteList(requestURI)) {

            // 로그인 확인
            HttpSession session = httpRequest.getSession(false);
            // 로그인하지 않은 사용자일 경우
            if (session == null || session.getAttribute("SESSION_KEY") == null) {
                throw new RuntimeException("로그인 해주세요.");
            }

            // 로그인 한 사용자일 경우
            log.info("로그인에 성공했습니다. 로그인된 사용자 요청: {}", requestURI);
        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
