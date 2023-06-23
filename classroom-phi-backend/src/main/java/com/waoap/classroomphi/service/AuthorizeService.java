package com.waoap.classroomphi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户认证服务接口。继承了 {@link UserDetailsService} 接口，其实现类需要提供用户认证和注册账户的业务。
 *
 * @author Waoap
 */
public interface AuthorizeService extends UserDetailsService {

  Integer registerAccount(String emailOrTelephone, String password, String role,
      String name, String school, String studentNo);
}
