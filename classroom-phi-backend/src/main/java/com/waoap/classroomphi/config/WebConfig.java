package com.waoap.classroomphi.config;

import com.waoap.classroomphi.interceptor.AuthorizeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类。
 *
 * @author Waoap
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  /**
   * 用户认证拦截器。
   */
  @Resource
  private AuthorizeInterceptor authorizeInterceptor;

  /**
   * 添加拦截器。
   *
   * @param registry 拦截器注册表
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authorizeInterceptor).addPathPatterns("/**");
  }
}
