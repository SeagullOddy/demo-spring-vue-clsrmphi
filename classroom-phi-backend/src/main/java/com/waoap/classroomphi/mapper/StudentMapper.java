package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.role.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface StudentMapper {

  /**
   * 创建学生。
   *
   * @param student 学生信息
   */
  @Insert("INSERT INTO student (no, account_id) VALUES (#{no}, #{accountId})")
  void createStudent(Student student);

  /**
   * 根据账户 id 查询学生信息。
   *
   * @param accountId 账户 id
   */
  @Select("SELECT * FROM student WHERE account_id = #{accountId}")
  Student findStudentByAccountId(Long accountId);
}
