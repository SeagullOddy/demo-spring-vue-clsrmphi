package com.waoap.classroomphi.service;

import com.waoap.classroomphi.entity.account.Account;
import com.waoap.classroomphi.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 提供 UserDetailService 实现来使用数据库中的账户信息进行用户认证，使用 {@link Service} 注解自动注入 IoC 容器，供验证使用。
 *
 * @author Waoap
 */
@Service
public class AccountAuthorizeService implements UserDetailsService {

  /**
   * 账户 Mapper 接口，使用注解实现自动装配。
   */
  @Resource
  private AccountMapper accountMapper;

  /**
   * 通过账户关键字获取账户信息。
   *
   * @param accountKey 账户关键字，可以是账号、邮箱或手机号
   * @return 账户信息
   * @throws UsernameNotFoundException 如果账户不存在或密码不匹配
   */
  @Override
  public UserDetails loadUserByUsername(String accountKey) throws UsernameNotFoundException {
    if (accountKey == null || "".equals(accountKey)) {
      throw new UsernameNotFoundException("邮箱/手机号/账号不能为空。");
    }
    Account account = accountMapper.findAccountByAccountKey(accountKey);
    if (account == null) {
      throw new UsernameNotFoundException("邮箱/手机号/账号或密码错误。");
    }
    return User.withUsername(account.getName())
        .password(account.getPassword())
        .roles(account.getRole().toString())
        .build();
  }
}
