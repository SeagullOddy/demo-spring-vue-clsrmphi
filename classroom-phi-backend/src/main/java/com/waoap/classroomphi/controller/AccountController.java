package com.waoap.classroomphi.controller;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.entity.account.Account;
import com.waoap.classroomphi.entity.account.RoleType;
import com.waoap.classroomphi.entity.role.Role;
import com.waoap.classroomphi.entity.role.Student;
import com.waoap.classroomphi.entity.role.Teacher;
import com.waoap.classroomphi.mapper.AccountMapper;
import com.waoap.classroomphi.mapper.StudentMapper;
import com.waoap.classroomphi.mapper.TeacherMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * 账户控制器，用于处理账户的相关请求，如果用户有权访问该控制器下的接口，表明其已经登录，因此 account 参数不可能为空。
 *
 * @author Waoap
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

  @Resource
  private AccountMapper accountMapper;

  /**
   * 获取当前登录的账户信息的后端接口。
   *
   * @param account 当前登录的账户 id，由拦截器注入 Session 中
   * @return 当前登录的账户信息
   */
  @GetMapping("/me")
  public RestBean<Account> me(@SessionAttribute("account") Account account) {
    return RestBean.success("访问成功", account);
  }

  @Resource
  private TeacherMapper teacherMapper;

  @Resource
  private StudentMapper studentMapper;

  /**
   * 获取当前登录账户的身份信息的后端接口。
   *
   * @param account 当前登录账户的 id，由拦截器注入 Session 中
   * @return 当前登录账户的身份信息
   */
  @GetMapping("/get-role")
  public RestBean<Role> getRole(@SessionAttribute("account") Account account) {
    if (account.getRole().equals(RoleType.TEACHER.name())) {
      Teacher teacher = teacherMapper.findTeacherByAccountId(account.getId());
      return RestBean.success("访问成功", teacher);
    } else if (account.getRole().equals(RoleType.STUDENT.name())) {
      Student student = studentMapper.findStudentByAccountId(account.getId());
      return RestBean.success("访问成功", student);
    }
    return RestBean.failure(1003, "账户身份类型未知");
  }
}
