package com.waoap.classroomphi.controller;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.entity.account.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 账户控制器，用于处理账户的相关请求。
 *
 * @author Waoap
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

  /**
   * 获取当前登录的账户信息的后端接口。
   *
   * @param account 当前登录账户的信息，由拦截器注入 Session 中
   * @return 当前登录的账户信息
   */
  @GetMapping("/me")
  public RestBean<Account> me(@SessionAttribute("account") Account account) {
    return RestBean.success(account);
  }
}
