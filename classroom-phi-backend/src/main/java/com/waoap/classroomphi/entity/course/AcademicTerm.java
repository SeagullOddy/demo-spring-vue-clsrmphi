package com.waoap.classroomphi.entity.course;

/**
 * @author Waoap
 */
public enum AcademicTerm {
  FULL_YEAR("全年"), FIRST_TERM("第一学期"), SECOND_TERM("第二学期");

  private final String name;

  AcademicTerm(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
