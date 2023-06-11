package com.waoap.classroomphi.entity;

import com.waoap.classroomphi.mapper.AccountMapper;
import lombok.Data;

/**
 * 账号的实体类。用户使用账号登录到平台，账号中存有用户对应的信息，如姓名 username、邮箱地址 email 等。
 *
 * <h2>备注</h2>
 *
 * 使用了 Lombok 的 {@link Data} 注解，自动生成 Getter、Setter、RequiredArgsConstructor、ToString、EqualAndHashCode
 * 等方法。
 *
 * @author Waoap
 * @see AccountMapper
 */
@Data
public class Account {

  private String id;

  private String username;

  private String email;

  private String telephone;

  private String password;

  private Role role;

  private String school;
}
