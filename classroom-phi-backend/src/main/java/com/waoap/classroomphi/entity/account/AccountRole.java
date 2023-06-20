package com.waoap.classroomphi.entity.account;

/**
 * 账户所属角色的枚举类。
 *
 * @author Waoap
 */
public enum AccountRole {
  TEACHER("老师"), STUDENT("学生");

  private final String value;

  AccountRole(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
