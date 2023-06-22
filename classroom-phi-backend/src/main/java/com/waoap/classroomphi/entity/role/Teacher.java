package com.waoap.classroomphi.entity.role;

import lombok.Data;

/**
 * @author Waoap
 */
@Data
public class Teacher implements Role {

  private Long id;

  private String no;

  private Long accountId;
}
