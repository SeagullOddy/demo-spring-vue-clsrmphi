package com.waoap.classroomphi.controller;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.entity.account.Account;
import com.waoap.classroomphi.entity.course.Course;
import com.waoap.classroomphi.mapper.AccountCourseMapper;
import com.waoap.classroomphi.mapper.CourseMapper;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  /**
   * 获取该账户相关的所有课程，结果以 Map 形式返回。
   *
   * @param account 账户
   * @return 所有课程
   */
  @GetMapping("/get-all")
  public RestBean<Map<String, List<Course>>> getAllCourse(
      @SessionAttribute("account") Account account) {
    Map<String, List<Course>> map = new HashMap<>();
    // 获取创建的课程
    List<Long> createdCourseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "CREATED");
    if (!createdCourseIds.isEmpty()) {
      map.put("createdCourses", courseMapper.findCoursesByIds(createdCourseIds));
    }
    // 获取学习的课程
    List<Long> learningCourseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "LEARNING");
    if (!learningCourseIds.isEmpty()) {
      map.put("learningCourses", courseMapper.findCoursesByIds(learningCourseIds));
    }
    // 获取助教的课程
    List<Long> assistingCourseIds = accountCourseMapper.findCourseIdsByRelationShip(account.getId(),
        "ASSISTING");
    if (!assistingCourseIds.isEmpty()) {
      map.put("assistingCourses", courseMapper.findCoursesByIds(assistingCourseIds));
    }
    // 获取置顶的课程
    List<Long> pinnedCourseIds = accountCourseMapper.findPinnedCourseIds(account.getId());
    if (!pinnedCourseIds.isEmpty()) {
      map.put("pinnedCourses", courseMapper.findCoursesByIds(pinnedCourseIds));
    }
    // 返回结果
    return RestBean.success("访问成功", map);
  }
}
