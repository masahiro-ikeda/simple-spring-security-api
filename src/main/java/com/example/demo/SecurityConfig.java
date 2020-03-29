package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Spring Security設定.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private AuthenticationSuccessHandler authenticationSuccessHandler;

  /**
   * 依存性の注入.
   *
   * @param loginSuccessHandler ハンドラ
   */
  @Autowired
  SecurityConfig(LoginSuccessHandler loginSuccessHandler) {
    this.authenticationSuccessHandler = loginSuccessHandler;
  }

  /**
   * 認証設定.
   *
   * @param http セキュリティ
   * @throws Exception 例外
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // 認可の設定
    http.authorizeRequests()
        .mvcMatchers("/security/login", "/")
        .permitAll()
        .anyRequest()
        .authenticated();

    // ログイン設定
    http.formLogin()
        .loginProcessingUrl("/security/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler(authenticationSuccessHandler).permitAll();

    // csrf設定
    http.csrf().disable();

    // ログアウト設定
    http.logout()
        .logoutUrl( "/security/logout" )
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl( "/login.html" );
        //.logoutSuccessHandler( new HttpStatusReturningLogoutSuccessHandler() );
  }

  /**
   * 認証設定.
   *
   * @param auth 認証オブジェクトのビルダー
   * @throws Exception 例外
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .passwordEncoder( NoOpPasswordEncoder.getInstance() )
        .withUser("develop")
        .password("password")
        .roles("USER");
  }
}
