package com.waoap.classroomphi.service.impl;

import com.waoap.classroomphi.entity.account.AuthorizeAccount;
import com.waoap.classroomphi.entity.account.RoleType;
import com.waoap.classroomphi.entity.role.Student;
import com.waoap.classroomphi.entity.role.Teacher;
import com.waoap.classroomphi.mapper.AccountMapper;
import com.waoap.classroomphi.mapper.StudentMapper;
import com.waoap.classroomphi.mapper.TeacherMapper;
import com.waoap.classroomphi.service.AuthorizeService;
import com.waoap.classroomphi.util.RandomImageUtil;
import com.waoap.classroomphi.util.RegexpUtil;
import jakarta.annotation.Resource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 实现 {@link AuthorizeService} 接口，提供用户认证和注册账户的业务。新版直接定义 Service 并注入就可以了，无需手动将它提交给
 * AuthenticationManager 进行管理
 *
 * @author Waoap
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {

  /**
   * 通过账户关键字获取认证账户信息。
   *
   * @param key 认证账户关键字，可以是账号、邮箱或手机号
   * @return 认证账户信息
   * @throws UsernameNotFoundException 如果认证账户不存在或密码不匹配
   */
  @Override
  public UserDetails loadUserByUsername(String key) throws UsernameNotFoundException {
    if (key == null || "".equals(key)) {
      throw new UsernameNotFoundException("邮箱/手机号/账号不能为空。");
    }
    AuthorizeAccount authorizeAccount = accountMapper.findAuthorizeAccountByKey(key);
    if (authorizeAccount == null) {
      throw new UsernameNotFoundException("邮箱/手机号/账号或密码错误。");
    }
    return User.withUsername(key) // username 使用用户登录时传入的 key，否则会导致 remember-me 失效
        .password(authorizeAccount.getPassword()) // 传入的密码会自动使用我们提供的加密器加密
        .roles(authorizeAccount.getRole()).build();
  }

  /**
   * 认证账户的 Mapper 接口，使用注解实现自动装配，用于操作账户表。
   */
  @Resource
  private AccountMapper accountMapper;

  /**
   * 老师 Mapper 接口，用于操作老师表。
   */
  @Resource
  private TeacherMapper teacherMapper;

  /**
   * 学生 Mapper 接口，用于操作学生表。
   */
  @Resource
  private StudentMapper studentMapper;

  /**
   * 注册认证账户业务，判断用户是否可以注册，可以则将信息写入数据库。
   *
   * @param emailOrTelephone 邮箱或手机号
   * @param password         密码
   * @param role             角色
   * @param name             姓名
   * @param school           学校
   * @param studentNo        学号
   * @return 注册操作状态，0 —— 注册成功，1000 —— 邮箱或手机号为空，1001 —— 邮箱或手机号已被注册，1002 —— 学号为空
   */
  @Override
  public Integer registerAccount(String emailOrTelephone, String password, String role,
      String name, String school, String studentNo) {
    // 判断用户是否可以注册
    // 邮箱或手机号不可为空
    if (Strings.isBlank(emailOrTelephone)) {
      return 1000;
    }
    // 邮箱或手机号已被注册
    else if (accountMapper.findAuthorizeAccountByKey(emailOrTelephone) != null) {
      return 1001;
    }
    // 指定学生身份时学号不可为空
    else if (role.equals(RoleType.STUDENT.name()) && studentNo == null) {
      return 1002;
    }

    // 创建账户，写入数据库
    // 确定新建的账户 ID
    AuthorizeAccount newAuthorizeAccount = new AuthorizeAccount();
    newAuthorizeAccount.setId(accountMapper.findNextId());
    // 账号格式为 ktp + 11 位数字，数字使用新账户 ID，不足 11 位前面补 0
    newAuthorizeAccount.setNo("ktp%011d".formatted(newAuthorizeAccount.getId()));
    newAuthorizeAccount.setName(name);
    newAuthorizeAccount.setPassword(
        new BCryptPasswordEncoder().encode(password)); // 这里密码需要加密，再写入数据库
    newAuthorizeAccount.setRole(role);
    newAuthorizeAccount.setSchool(school);
    newAuthorizeAccount.setAvatar(RandomImageUtil.getRandomAvatar());
    if (emailOrTelephone.matches(RegexpUtil.EMAIL_REGEXP)) {
      newAuthorizeAccount.setEmail(emailOrTelephone);
      accountMapper.createAccountByEmail(newAuthorizeAccount);
    } else if (emailOrTelephone.matches(RegexpUtil.TELEPHONE_REGEXP)) {
      newAuthorizeAccount.setTelephone(emailOrTelephone);
      accountMapper.createAccountByTelephone(newAuthorizeAccount);
    }

    // 创建对应的老师/学生，写入数据库
    if (role.equals(RoleType.TEACHER.name())) {
      Teacher newTeacher = new Teacher();
      newTeacher.setAccountId(newAuthorizeAccount.getId());
      teacherMapper.createTeacher(newTeacher);
    } else if (role.equals(RoleType.STUDENT.name())) {
      Student newStudent = new Student();
      newStudent.setNo(studentNo);
      newStudent.setAccountId(newAuthorizeAccount.getId());
      studentMapper.createStudent(newStudent);
    }

    return 0;
  }
}
