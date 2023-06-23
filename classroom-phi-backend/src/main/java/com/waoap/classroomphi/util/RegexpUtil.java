package com.waoap.classroomphi.util;

/**
 * 正则表达式工具类，邮箱、电话正则表达式来源于：<a href="https://c.runoob.com/front-end/854/">菜鸟教程</a>。
 *
 * @author Waoap
 */
public class RegexpUtil {

  public static final String EMAIL_OR_TELEPHONE_REGEXP = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$|^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

  public static final String EMAIL_REGEXP = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

  public static final String TELEPHONE_REGEXP = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

  public static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[ -/:-@\\[-`{-~]).*$";

  public static final String ROLE_REGEXP = "^(老师|学生)$";
}
