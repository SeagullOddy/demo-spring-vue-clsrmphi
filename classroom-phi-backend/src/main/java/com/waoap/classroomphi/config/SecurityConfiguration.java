package com.waoap.classroomphi.config;

import com.alibaba.fastjson2.JSONObject;
import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.service.AccountAuthorizeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 *
 * <h2>备注</h2>
 *
 * Spring Security 新版本的变化很大，本项目使用的是 Spring Security 6.1.0 版本， 原来继承 WebSecurityConfigurerAdapter
 * 类（此类在 6.1.0 版本中已经被删除），重写 configure 方法的方式已经弃用，转而使用给配置类添加
 * {@link Configuration}、{@link EnableWebSecurity} 注解并暴露返回 {@link SecurityFilterChain} 实例的 Bean
 * 方法的方式。且 HttpSecurity 的配置方式也有所变化，原有的建造者模式改为了链式配置配合 lambda 表达式语法的模式。此外，新版本不需要主动暴露
 * AuthenticationManagerBean 方法，也不需要手动实例化 Service，使用注解注入即可。
 *
 * @author Waoap
 * @since JDK 17
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  /**
   * 账号授权服务类，使用注解注入的方式，不需要手动实例化
   */
  @Resource
  private AccountAuthorizeService accountAuthorizeService;

  /**
   * 配置过滤器链，过滤请求
   *
   * @param httpSecurity HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception 异常
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // 所有请求都需要授权
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
        // 指定登录配置
        .formLogin(formLogin -> formLogin.loginProcessingUrl("/api/auth/login")
            .successHandler(this::onAuthenticationSuccess)
            .failureHandler(this::onAuthenticationFailure))
        // 指定登出配置
        .logout(logout -> logout.logoutUrl("/api/auth/logout"))
        // 关闭 csrf，前后端分离项目不需要 csrf
        .csrf(AbstractHttpConfigurer::disable)
        // 无权限异常的处理
        .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
            this::onAuthenticationFailure));
    return httpSecurity.build();
  }

  /**
   * 配置密码加密器
   *
   * @return BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 登录成功执行此方法
   *
   * @param ignoredHttpServletRequest HttpServletRequest
   * @param httpServletResponse       HttpServletResponse
   * @param ignoredAuthentication     Authentication
   * @throws IOException if an input or output exception occurs
   */
  private void onAuthenticationSuccess(HttpServletRequest ignoredHttpServletRequest,
      HttpServletResponse httpServletResponse, Authentication ignoredAuthentication)
      throws IOException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
  }

  /**
   * 登录失败执行此方法
   *
   * @param ignoredHttpServletRequest HttpServletRequest
   * @param httpServletResponse       HttpServletResponse
   * @param e                         AuthenticationException
   * @throws IOException if an input or output exception occurs
   */
  private void onAuthenticationFailure(HttpServletRequest ignoredHttpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.getWriter()
        .write(JSONObject.toJSONString(RestBean.failure(401, e.getMessage())));
  }
}
