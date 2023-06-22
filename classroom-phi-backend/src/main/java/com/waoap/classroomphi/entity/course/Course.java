package com.waoap.classroomphi.entity.course;

import lombok.Data;


/**
 * @author Waoap
 */
@Data
public class Course {

  /**
   * 课程 id
   */
  private Long id;

  /**
   * 加课码的 id
   */
  private Long codeId;

  /**
   * 课程名称
   */
  private String name;

  /**
   * 课程教学班级
   */
  private String teachingClass;

  /**
   * 课程学年，格式如：2019-2020
   */
  private Character[] academicYear;

  /**
   * 课程学期
   */
  private AcademicTerm academicTerm;

  /**
   * 课程教学模式
   */
  private TeachingMode teachingMode;

  /**
   * 课程学时
   */
  private Integer academicHour;

  /**
   * 课程教学地点
   */
  private String teachingPlace;

  /**
   * 是否开启机构相关
   */
  private Boolean institutionRelated;

  /**
   * 课程描述
   */
  private String description;

  /**
   * 是否允许学生退课
   */
  private Boolean allowDrop;

  /**
   * 课程人数上限
   */
  private Integer headcountLimit;

  /**
   * 是否开启课程人数上限
   */
  private Boolean enableHeadcountLimit;

  /**
   * 课程皮肤
   */
  private String skin;
}
