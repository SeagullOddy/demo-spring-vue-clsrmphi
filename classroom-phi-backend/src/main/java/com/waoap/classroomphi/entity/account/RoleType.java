package com.waoap.classroomphi.entity.account;

/**
 * 账户所属角色的枚举类。
 *
 * @author Waoap
 */
public enum RoleType {
  TEACHER("老师"), STUDENT("学生");

  private final String name;

  RoleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
