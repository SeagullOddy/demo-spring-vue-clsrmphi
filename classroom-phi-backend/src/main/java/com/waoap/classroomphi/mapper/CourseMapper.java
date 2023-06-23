package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.course.Course;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface CourseMapper {

  /**
   * 根据课程 id 查询对应的课程
   *
   * @param id 课程 id
   * @return 课程
   */
  @Select("SELECT * FROM course WHERE id = #{id}")
  @Results({
      @Result(property = "codeId", column = "code_id"),
      @Result(property = "teachingClass", column = "teaching_class"),
      @Result(property = "academicYear", column = "academic_year"),
      @Result(property = "academicTerm", column = "academic_term"),
      @Result(property = "teachingMode", column = "teaching_mode"),
      @Result(property = "academicHour", column = "academic_hour"),
      @Result(property = "teachingPlace", column = "teaching_place"),
      @Result(property = "institutionRelated", column = "institution_related"),
      @Result(property = "allowDrop", column = "allow_drop"),
      @Result(property = "headcountLimit", column = "headcount_limit"),
      @Result(property = "enableHeadcountLimit", column = "enable_headcount_limit"),
  })
  Course findCourseById(Long id);

  /**
   * 根据课程 id 列表查询对应的课程列表
   *
   * @param ids 课程 id 列表
   * @return 课程列表
   */
  @Select("<script>"
      + "SELECT * FROM course WHERE id IN " +
      "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
      + "#{id}"
      + "</foreach>"
      + "</script>")
  @Results({
      @Result(property = "codeId", column = "code_id"),
      @Result(property = "teachingClass", column = "teaching_class"),
      @Result(property = "academicYear", column = "academic_year"),
      @Result(property = "academicTerm", column = "academic_term"),
      @Result(property = "teachingMode", column = "teaching_mode"),
      @Result(property = "academicHour", column = "academic_hour"),
      @Result(property = "teachingPlace", column = "teaching_place"),
      @Result(property = "institutionRelated", column = "institution_related"),
      @Result(property = "allowDrop", column = "allow_drop"),
      @Result(property = "headcountLimit", column = "headcount_limit"),
      @Result(property = "enableHeadcountLimit", column = "enable_headcount_limit"),
  })
  List<Course> findCoursesByIds(@Param("ids") List<Long> ids);
}
