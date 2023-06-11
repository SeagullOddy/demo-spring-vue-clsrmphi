package com.waoap.classroomphi.service;

import com.waoap.classroomphi.entity.Account;
import com.waoap.classroomphi.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户账号授权服务类
 *
 * @author Waoap
 */
@Service
public class AccountAuthorizeService implements UserDetailsService {

  /**
   * 账号 Mapper 接口，使用注解实现自动装配
   */
  @Resource
  private AccountMapper accountMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username == null || "".equals(username)) {
      throw new UsernameNotFoundException("用户名不能为空");
    }
    Account account = accountMapper.findAccountByUsernameOrEmail(username);
    if (account == null) {
      throw new UsernameNotFoundException("用户名或密码错误");
    }
    return User.withUsername(account.getUsername())
        .password(account.getPassword())
        .roles(account.getRole().toString())
        .build();
  }
}
