package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.code.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface CodeMapper {

  @Select("SELECT * FROM code WHERE id = #{id}")
  Code findCodeById(Long id);
}
