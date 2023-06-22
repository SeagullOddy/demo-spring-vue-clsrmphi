package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.role.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface TeacherMapper {

  /**
   * 创建老师。
   *
   * @param teacher 老师信息
   */
  @Insert("INSERT INTO teacher(account_id) VALUES (#{accountId})")
  void createTeacher(Teacher teacher);

  /**
   * 根据账户 id 查询老师信息。
   *
   * @param accountId 账户 id
   */
  @Select("SELECT * FROM teacher WHERE account_id = #{accountId}")
  Teacher findTeacherByAccountId(Long accountId);
}
