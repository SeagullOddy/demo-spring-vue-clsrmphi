package com.waoap.classroomphi.controller;

import static com.waoap.classroomphi.util.RegexpUtil.EMAIL_OR_TELEPHONE_REGEXP;
import static com.waoap.classroomphi.util.RegexpUtil.PASSWORD_REGEXP;
import static com.waoap.classroomphi.util.RegexpUtil.ROLE_REGEXP;

import com.waoap.classroomphi.entity.RestBean;
import com.waoap.classroomphi.service.AuthorizeService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证控制器。
 *
 * <h2>备注</h2>
 *
 * 虽然在前端已经有数据验证，但是为了防止用户使用 post 等请求绕过前端验证，所以在后端也必须要进行数据验证。
 *
 * @author Waoap
 */
@Validated // 开启数据验证
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

  /**
   * 用户认证服务。
   */
  @Resource
  private AuthorizeService authorizeService;

  /**
   * 注册账户的后端接口，使用用户认证服务实现用户注册业务。
   *
   * @param emailOrTelephone 邮箱或手机号
   * @param password         密码
   * @param role             身份
   * @param name             姓名
   * @param school           学校/机构
   * @param studentNo        学号
   * @return 注册结果
   */
  @PostMapping("/register")
  public RestBean<String> registerAccount(
      @Pattern(regexp = EMAIL_OR_TELEPHONE_REGEXP) @Length(max = 30) @RequestParam String emailOrTelephone,
      @Pattern(regexp = PASSWORD_REGEXP) @Length(min = 8, max = 20) @RequestParam String password,
      @Pattern(regexp = ROLE_REGEXP) @Nonnull @RequestParam String role,
      @Length(min = 2, max = 20) @RequestParam String name,
      @Length(min = 2, max = 50) @RequestParam String school,
      @Length(min = 5, max = 20) @RequestParam(required = false) String studentNo) {
    // 执行用户注册业务
    Integer status = authorizeService.registerAccount(emailOrTelephone, password,
        role, name, school, studentNo);
    // 根据注册结果返回不同的消息
    switch (status) {
      case 0 -> {
        return RestBean.success("注册成功");
      }
      case 1000 -> {
        return RestBean.failure(status, "邮箱或手机号不为空");
      }
      case 1001 -> {
        return RestBean.failure(status, "邮箱或手机号已被注册");
      }
      case 1002 -> {
        return RestBean.failure(status, "指定学生身份时学号不可为空");
      }
      default -> {
        return RestBean.failure(status, "未知错误");
      }
    }
  }
}
