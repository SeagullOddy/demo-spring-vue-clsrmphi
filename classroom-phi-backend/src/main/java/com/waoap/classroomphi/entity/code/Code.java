package com.waoap.classroomphi.entity.code;

import lombok.Data;

/**
 * @author Waoap
 */
@Data
public class Code {

  private Long id;

  private Character[] code;

  private Boolean disabled;
}
