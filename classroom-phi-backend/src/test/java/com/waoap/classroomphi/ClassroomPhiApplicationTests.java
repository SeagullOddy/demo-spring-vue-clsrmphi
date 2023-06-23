package com.waoap.classroomphi;

import com.waoap.classroomphi.entity.course.Course;
import com.waoap.classroomphi.mapper.AccountCourseMapper;
import com.waoap.classroomphi.mapper.CodeMapper;
import com.waoap.classroomphi.mapper.CourseMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 应用程序测试。
 *
 * @author Waoap
 */
@SpringBootTest
class ClassroomPhiApplicationTests {

  @Resource
  private AccountCourseMapper accountCourseMapper;

  @Resource
  private CourseMapper courseMapper;

  @Resource
  private CodeMapper codeMapper;

  @Test
  void contextLoads() {
    Course course = courseMapper.findCourseById(1L);
    System.out.println(course);
    System.out.println(course.getCodeId());
    System.out.println(codeMapper.findCodeById(course.getCodeId()));
  }
}
