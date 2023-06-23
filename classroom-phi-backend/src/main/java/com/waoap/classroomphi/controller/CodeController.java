package com.waoap.classroomphi.controller;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.entity.code.Code;
import com.waoap.classroomphi.entity.course.Course;
import com.waoap.classroomphi.mapper.CodeMapper;
import com.waoap.classroomphi.mapper.CourseMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Waoap
 */
@RestController
@RequestMapping("/api/code")
public class CodeController {

  @Resource
  private CourseMapper courseMapper;

  @Resource
  private CodeMapper codeMapper;

  @GetMapping("/find")
  public RestBean<Code> findCodeByCourseId(@RequestParam Long courseId) {
    Course course = courseMapper.findCourseById(courseId);
    if (course == null) {
      return RestBean.failure(1004, "课程不存在");
    } else {
      return RestBean.success("访问成功", codeMapper.findCodeById(course.getCodeId()));
    }
  }
}
