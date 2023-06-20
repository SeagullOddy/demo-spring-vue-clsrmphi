package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Waoap
 */
@Mapper
public interface TeacherMapper {

  @Insert("INSERT INTO teacher(account_id) VALUES (#{accountId})")
  void createTeacher(Teacher teacher);
}
