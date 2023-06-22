package com.waoap.classroomphi.controller;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.entity.account.Account;
import com.waoap.classroomphi.entity.course.Course;
import com.waoap.classroomphi.mapper.AccountCourseMapper;
import com.waoap.classroomphi.mapper.CourseMapper;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * @author Waoap
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {

  @Resource
  private AccountCourseMapper accountCourseMapper;

  @Resource
  private CourseMapper courseMapper;

  @GetMapping("/get-created")
  public RestBean<List<Course>> getCreatedCourse(@SessionAttribute("account") Account account) {
    List<Long> courseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "CREATED");
    return RestBean.success("访问成功", courseMapper.findCoursesByIds(courseIds));
  }

  @GetMapping("/get-learning")
  public RestBean<List<Course>> getLearningCourse(@SessionAttribute("account") Account account) {
    List<Long> courseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "LEARNING");
    return RestBean.success("访问成功", courseMapper.findCoursesByIds(courseIds));
  }

  @GetMapping("/get-assisting")
  public RestBean<List<Course>> getAssistingCourse(@SessionAttribute("account") Account account) {
    List<Long> courseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "ASSISTING");
    return RestBean.success("访问成功", courseMapper.findCoursesByIds(courseIds));
  }

  @GetMapping("/get-pinned")
  public RestBean<List<Course>> getPinnedCourse(@SessionAttribute("account") Account account) {
    List<Long> courseIds = accountCourseMapper.findPinnedCourseIds(account.getId());
    return RestBean.success("访问成功", courseMapper.findCoursesByIds(courseIds));
  }
}
