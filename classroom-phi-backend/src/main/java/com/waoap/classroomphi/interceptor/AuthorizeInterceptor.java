package com.waoap.classroomphi.interceptor;

import com.waoap.classroomphi.controller.AccountController;
import com.waoap.classroomphi.entity.account.Account;
import com.waoap.classroomphi.mapper.AccountMapper;
import com.waoap.classroomphi.service.impl.AuthorizeServiceImpl;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用于用户认证的拦截器。
 *
 * @author Waoap
 */
@Component
public class AuthorizeInterceptor implements HandlerInterceptor {

  @Resource
  AccountMapper accountMapper;

  /**
   * 拦截器的前置处理，拦截用户认证时所使用的 username，实际是 accountNo 账号（于
   * {@link AuthorizeServiceImpl#loadUserByUsername(String)} 中指定），并根据它查询对应的账户（不是认证账户），保存在 session
   * 会话中，供 {@link AccountController} 使用。
   *
   * @param request  current HTTP request
   * @param response current HTTP response
   * @param handler  chosen handler to execute, for type and/or instance evaluation
   * @return {@code true} if the execution chain should proceed with the
   */
  @Override
  public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response,
      @Nonnull Object handler) {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    // getName 获取的实际上是 accountNo 账号，通过它查询对应的账户，放入 session 中
    Account account = accountMapper.findAccountByKey(authentication.getName());
    request.getSession().setAttribute("account", account);
    return true;
  }
}
