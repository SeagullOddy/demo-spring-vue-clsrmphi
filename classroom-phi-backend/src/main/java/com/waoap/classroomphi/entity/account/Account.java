package com.waoap.classroomphi.entity.account;

import com.waoap.classroomphi.mapper.AccountMapper;
import lombok.Data;

/**
 * 账户的实体类。一个账户代表一个用户，用户使用账号登录到平台，账号中存有用户对应的信息，如真实姓名 username、邮箱地址 email 等。
 *
 * <h2>Lombok</h2>
 *
 * 使用了 Lombok 的 {@link Data} 注解，自动生成 Getter、Setter、RequiredArgsConstructor、ToString、EqualAndHashCode
 * 等方法。
 *
 * @author Waoap
 * @see AccountMapper
 */
@Data
public class Account {

  /**
   * 账户的唯一标识符，用户不可见，使用自增主键。
   */
  private Long id;

  /**
   * 账号，用户可见，格式为：ktp + 11 位数字。
   */
  private String no;

  /**
   * 账户名称，用户的真实姓名。
   */
  private String name;

  /**
   * 账户的电子邮件。
   */
  private String email;

  /**
   * 账户的手机号码。
   */
  private String telephone;

  /**
   * 账户的密码。
   */
  private String password;

  /**
   * 账户所属角色。
   *
   * @see Role
   */
  private Role role;

  /**
   * 账户所属学校或组织。
   */
  private String school;

  /**
   * 账户所属学院。
   */
  private String faculty;

  /**
   * 账户所属专业。
   */
  private String major;

  /**
   * 账户的头像。
   */
  private Byte[] avatar;
}
