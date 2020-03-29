package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
  }

  /**
   * ログイン成功時の挙動を定義.
   *
   * @param request リクエスト
   * @param response レスポンス
   * @param authentication 認証情報
   * @throws IOException 例外
   * @throws ServletException 例外
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    // HTTPヘッダーの設定
    response.setHeader("Content-Type", "application/json;charset=UTF-8");

    // 簡易的なクロスドメイン対策
    response.setHeader("Access-Control-Allow-Origin", "http://localhost");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Allow-Credentials", "true");

    // HTTPステータスの設定
    response.setStatus( HttpStatus.OK.value());

    // レスポンスボディの設定
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("message", "success!!");
    String payLoad = new ObjectMapper().writeValueAsString(responseBody);
    response.getWriter().write(payLoad);
  }
}
