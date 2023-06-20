package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Waoap
 */
@Mapper
public interface StudentMapper {

  @Insert("INSERT INTO student (no, account_id) VALUES (#{no}, #{accountId})")
  void createStudent(Student student);
}
