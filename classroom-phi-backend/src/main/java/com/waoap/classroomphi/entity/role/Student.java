package com.waoap.classroomphi.entity.role;

import java.sql.Date;
import lombok.Data;

/**
 * @author Waoap
 */
@Data
public class Student implements Role {

  private Long id;

  private String no;

  private Long accountId;

  private Date grade;

  private String clazz;

  /**
   * 学生的入学时间
   */
  private Date admissionDate;
}
