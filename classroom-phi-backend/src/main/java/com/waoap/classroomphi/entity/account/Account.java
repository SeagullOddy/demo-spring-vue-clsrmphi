package com.waoap.classroomphi.entity.account;

import lombok.Data;

/**
 * 账户的实体类，适用于用户需要查询个人账户信息的场景。需要进行用户认证时请使用 {@link AuthorizeAccount}。
 *
 * @author Waoap
 */
@Data
public class Account {

  private Long id;

  private String no;

  private String name;

  private String email;

  private String telephone;

  private RoleType roleType;

  private String school;

  private String faculty;

  private String major;

  private String avatar;
}
