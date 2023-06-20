package com.waoap.classroomphi.config;

import com.alibaba.fastjson2.JSONObject;
import com.waoap.classroomphi.entity.RestBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 的配置类
 *
 * <h2>使用注解开发 SpringBoot</h2>
 *
 * <h3>创建配置类（组件类、服务类等同理）</h3>
 *
 * SpringBoot 官方更推荐使用全注解开发。给配置类添加 {@link Configuration} 注解可以告诉 SpringBoot 这是一个配置类（一个配置类等价于一个传统的 XML
 * 配置文件），配置类本身也是容器中的组件，SpringBoot 会自动将其注入到 IoC 容器中。
 *
 * <h3>向 IoC 容器注入 Bean</h3>
 *
 * 在配置类中给有一个对象作为返回值的方法添加 {@link Bean}，该方法返回的对象将被 SpringBoot 自动注入到 IoC
 * 容器中，此方法同样适用且仅适用与组件类、配置类、服务类等中，在使用此方法向 IoC 容器注入 Bean 之后，还可以使用如 {@link PostConstruct} 和
 * {@link PreDestroy} 等注解，实现在 Bean 初始化和销毁时执行指定的方法，效果和 init-method、destroy-method 等价：
 *
 * <pre>
 *   &#64;Bean // Foo 类会被注入到 IoC 容器中
 *   public Foo foo() {
 *     return new Foo();
 *   }
 * </pre>
 *
 * 或使用 {@link Component} 等注解将一个类作为组件类等，注入到 IoC 容器中，例如：
 *
 * <pre>
 *   &#64;Component // Foo 类会被注入到 IoC 容器中
 *   public class Foo {
 *     // ...
 *   }
 * </pre>
 *
 * 或使用 {@link Import} 注解导入一个任意的类并注入到 IoC 容器中（如果是配置类等，则其中的所有 Bean 都会被同样注入到 IoC 容器），例如：
 *
 * <pre>
 *   &#64;Import(Foo.class) // Foo 类会被注入到 IoC 容器中
 *   &#64;Configuration
 *   public class Config {
 *     // ...
 *   }
 *
 * <h3>从 IoC 容器取用 Bean（自动装配）</h3>
 *
 * 在使用 {@link Bean} 注解向 IoC 容器注入 Bean 的方法中，如果需要使用其他的 Bean，可以直接将其作为方法的形参，SpringBoot 会自动从 IoC 容器中取出所需的 Bean，装配给形参供该方法使用：
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
 *   &#64;Bean // 向 IoC 容器注入 Foo 对象
 *   public Foo foo() {
 *     return new Foo();
 *   }
 *
 *   &#64;Bean
 *   public Bar bar(Foo foo) { // foo 会被自动装配，等价于 bar(&#64;Autowired Foo foo)
 *     return new Bar(foo);
 *   }
 *   </pre>
 *
 * 或者使用 {@link Resource} 或 {@link Autowired} 注解，实现自动装配，例如：
 *
 * <pre>
 * class Foo {
 *   // ...
 * }
 *
 * class Bar {
 *
 *  &#64;Resource
 *  private Foo foo;
 *
 *  // ...
 *  }
 *
 *  &#64;Bean
 *  public Foo foo() {
 *    return new Foo(); // 1. 向 IoC 容器注入 Foo 对象
 *  }
 *
 *  &#64;Bean
 *  public Bar bar() {
 *    return new Bar(); // 2. 创建 Bar 对象所需的 Foo 对象会被自动装配
 *  }
 *
 * </pre>
 *
 * <h2>Spring Security</h2>
 *
 * 本项目使用的是 Spring Security 6.1.0 版本， 原来继承 WebSecurityConfigurerAdapter 类（此类在 6.1.0 版本中已经被删除）并重写 configure 方法来配置 WebSecurity 和 SecurityFilterChain 过滤器链的方式已经弃用，转而使用 {@link EnableWebSecurity} 注解和实现 WebSecurityCustomizer、SecurityFilterChain 的 Bean 方法（详见 {@link EnableWebSecurity} 注解的文档）。此外，过滤器链的配置方式也有所变化，由链式调用改为了基于 lambda 表达式的配置方式。
 *
 * @author Waoap
 * @since JDK 17
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * 配置密码加密器，会在验证时被自动装配自动使用。
   *
   * @return BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 配置持久化令牌存储库。
   *
   * @param dataSource 数据源，由 Spring 自动装配
   * @return PersistentTokenRepository
   */
  @Bean
  public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
    JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
    jdbcTokenRepository.setDataSource(dataSource);
    // 启动时创建表，第一次启动时需要，后续启动时改为 false
    jdbcTokenRepository.setCreateTableOnStartup(false);
    return jdbcTokenRepository;
  }

  /**
   * 配置过滤器链。
   *
   * @param httpSecurity              HttpSecurity
   * @param persistentTokenRepository 持久化令牌存储库
   * @return SecurityFilterChain
   * @throws Exception 异常
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
      PersistentTokenRepository persistentTokenRepository) throws Exception {
    httpSecurity
        // 指定授权配置
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests.requestMatchers("/api/auth/**")
                .permitAll()
                .anyRequest()
                .authenticated())
        // 指定登录配置
        .formLogin(formLogin -> formLogin.loginProcessingUrl("/api/auth/login")
            .successHandler(this::onLoginSuccess)
            .failureHandler(this::onLoginFailure))
        // 指定登出配置
        .logout(logout -> logout.logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(this::onLogoutSuccess))
        // 指定记住我配置
        .rememberMe(rememberMe -> rememberMe.rememberMeParameter("remember")
            .tokenValiditySeconds(60 * 60 * 24 * 7)
            .tokenRepository(persistentTokenRepository))
        // 前后端分离，由于前端后端总是分处于两台服务器上，之间的请求可能需要跨域，应关闭 csrf
        .csrf(AbstractHttpConfigurer::disable)
        // 允许跨域请求
        .cors(cors -> cors.configurationSource(request -> {
          CorsConfiguration corsConfiguration = new CorsConfiguration();
          // 允许跨域的域名，最好指定请求头和请求方法
          corsConfiguration.addAllowedOriginPattern("http://localhost:5173");
          corsConfiguration.setAllowCredentials(true); // 允许携带 cookie
          corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
          corsConfiguration.addAllowedMethod("*"); // 允许所有请求方法
          // 为了方便，所有路径都使用该配置
          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration("/**", corsConfiguration);
          return corsConfiguration;
        }))
        // 无权限异常的处理
        .exceptionHandling(
            exceptionHandling -> exceptionHandling.authenticationEntryPoint(this::onLoginFailure));
    return httpSecurity.build();
  }


  /**
   * 登录成功时执行此方法返回结果。
   *
   * @param ignoredHttpServletRequest HttpServletRequest
   * @param httpServletResponse       HttpServletResponse
   * @param ignoredAuthentication     Authentication
   * @throws IOException if an input or output exception occurs
   */
  private void onLoginSuccess(HttpServletRequest ignoredHttpServletRequest,
      HttpServletResponse httpServletResponse, Authentication ignoredAuthentication)
      throws IOException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.getWriter().write(JSONObject.toJSONString(RestBean.success("登录成功")));
  }

  /**
   * 登录失败时执行此方法返回结果。
   *
   * @param ignoredHttpServletRequest HttpServletRequest
   * @param httpServletResponse       HttpServletResponse
   * @param e                         AuthenticationException
   * @throws IOException if an input or output exception occurs
   */
  private void onLoginFailure(HttpServletRequest ignoredHttpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.getWriter()
        .write(JSONObject.toJSONString(RestBean.failure(401, e.getMessage())));
  }

  /**
   * 登出成功时执行此方法返回结果。
   *
   * @param httpServletRequest    HttpServletRequest
   * @param httpServletResponse   HttpServletResponse
   * @param ignoredAuthentication Authentication
   * @throws IOException if an input or output exception occurs
   */
  private void onLogoutSuccess(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Authentication ignoredAuthentication)
      throws IOException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    // 用户登出成功，清除 session 中的 account
    httpServletRequest.getSession().removeAttribute("account");
    httpServletResponse.getWriter().write(JSONObject.toJSONString(RestBean.success("登出成功")));
  }
}
