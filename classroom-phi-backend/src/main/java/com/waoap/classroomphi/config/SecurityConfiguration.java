package com.waoap.classroomphi.config;

import com.alibaba.fastjson2.JSONObject;
import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.service.AccountAuthorizeService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * Spring Security 的配置类
 *
 * <h2>备注</h2>
 *
 * 在使用 SpringBoot 框架的时候，类似 MyBatis 框架，同样有使用注解和使用 XML 配置文件两种提供配置的方式。本项目使用注解，需要给配置类添加
 * {@link Configuration} 注解，告诉 SpringBoot 这是一个配置类（一个配置类等价于一个 application.xml）。而后在类中添加带有 {@link Bean}
 * 注解的方法，该方法返回的对象将被自动注入到 IoC 容器中。如果需要引入其他 Bean 进行的注入，可以直接将其作为方法形参，SpringBoot 会自动为其装配，例如：
 *
 * <pre>
 *   class Foo {
 *     // ...
 *   }
 *
 *   class Bar {
 *
 *     private Foo foo;
 *
 *     public Bar(Foo foo) {
 *       this.foo = foo;
 *     }
 *
 *     // ...
 *   }
 *
 *   &#64;Bean
 *   public Foo foo() {
 *     return new Foo();
 *   }
 *
 *   &#64;Bean
 *   public Bar bar(Foo foo) { // foo 会被自动注入，取自上面的 foo 方法
 *     return new Bar(foo);
 *   }
 *   </pre>
 *
 * 或者在被注入为 Bean 的类中使用 {@link Resource} 或 {@link Autowired} 注解实现自动装配，例如：
 *
 * <pre>
 * class Foo {
 *   // ...
 * }
 *
 * class Bar {
 *
 *  &#64;Resource // foo 会被自动装配，取自下面的 foo 方法
 *  private Foo foo;
 *
 *  // ...
 *  }
 *
 *  &#64;Bean
 *  public Foo foo() {
 *    return new Foo();
 *  }
 *
 *  &#64;Bean
 *  public Bar bar() {
 *    return new Bar();
 *  }
 *
 * </pre>
 *
 * 此外，还可以使用如 {@link PostConstruct} 和 {@link PreDestroy} 等注解，实现在 Bean 初始化和销毁时执行指定方法，效果和
 * init-method、destroy-method 等价。进一步，还可以在 Bean 类上使用 {@link Component} 注解，配置类上使用
 * {@link ComponentScan} 注解指定扫描的包，实现完全自动装配。
 * <p>
 * Spring Security 新版本的变化很大，本项目使用的是 Spring Security 6.1.0 版本， 原来继承 WebSecurityConfigurerAdapter
 * 类（此类在 6.1.0 版本中已经被删除），重写 configure 方法的方式已经弃用，转而使用给配置类添加 {@link EnableWebSecurity} 注解的方式。且
 * HttpSecurity 的配置方式也有所变化，原有的无参的配置方法弃用，使用重写的需要提供具体配置的新方法作为代替，并弃用了 and 方法等。
 *
 * @author Waoap
 * @since JDK 17
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  /**
   * 账号授权服务类，使用注解实现自动装配
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
    return httpSecurity
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
            this::onAuthenticationFailure))
        // 构建
        .build();
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
