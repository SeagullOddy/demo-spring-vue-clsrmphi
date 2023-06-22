package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.course.Course;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface CourseMapper {

  /**
   * 根据课程 id 列表查询对应的课程列表
   *
   * @param ids 课程 id 列表
   * @return 课程列表
   */
  @Select({"<script>", "SELECT * FROM course WHERE id IN",
      "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>",
      "#{id}", "</foreach>", "</script>"})
  List<Course> findCoursesByIds(@Param("ids") List<Long> ids);
}
