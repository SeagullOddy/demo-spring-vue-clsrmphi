package com.waoap.classroomphi.entity.account;

import lombok.Data;

/**
 * 认证账户的实体类。认证账户是在实际操作数据库中使用到的，是数据库中真实存储的账户，拥有数据库中账户的所有信息。认证账户应当仅在用户认证（如登陆、注册、重置密码）等需要访问数据库中账户全部信息时使用。用户需要查询个人账户信息时请使用{@link
 * Account}。
 *
 * <h2>Lombok</h2>
 *
 * 使用了 Lombok 的 {@link Data} 注解，自动生成 Getter、Setter、RequiredArgsConstructor、ToString、EqualAndHashCode
 * 等方法。
 *
 * @author Waoap
 */
@Data
public class AuthorizeAccount {

  /**
   * 账户的唯一标识符，使用自增主键。
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
   */
  private String role;

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
   * 账户的头像，存储的是其网址。
   */
  private String avatar;
}
