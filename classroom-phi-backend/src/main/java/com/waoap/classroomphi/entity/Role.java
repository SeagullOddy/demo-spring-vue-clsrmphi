package com.waoap.classroomphi.entity;

/**
 * 角色枚举类。
 *
 * @author Waoap
 */
public enum Role {
  TEACHER("老师"), STUDENT("学生");

  private final String role;

  Role(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return role;
  }
}
