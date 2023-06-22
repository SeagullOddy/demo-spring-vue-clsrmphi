package com.waoap.classroomphi.entity.course;

/**
 * @author Waoap
 */
public enum TeachingMode {
  ONLINE("线上"), OFFLINE("线下"), MIXED("混合");

  private final String name;

  TeachingMode(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
