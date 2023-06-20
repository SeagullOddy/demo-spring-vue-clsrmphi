package com.waoap.classroomphi.entity;

import lombok.Data;

/**
 * RestBean 后端接口返回结果类，用于封装、规范后端接口的返回结果。
 *
 * @author Waoap
 */
@Data
public class RestBean<T> {

  private Integer status;

  private Boolean success;

  private T result;

  private RestBean(Integer status, Boolean success, T result) {
    this.status = status;
    this.success = success;
    this.result = result;
  }

  /**
   * 返回一个表示成功的结果。
   *
   * @param <T> 返回结果的类型
   * @return 表示成功的 RestBean 对象
   */
  public static <T> RestBean<T> success() {
    return new RestBean<>(200, true, null);
  }

  /**
   * 返回一个表示成功的结果。
   *
   * @param result 返回的结果
   * @param <T>    返回结果的类型
   * @return 表示成功的 RestBean 对象
   */
  public static <T> RestBean<T> success(T result) {
    return new RestBean<>(200, true, result);
  }

  /**
   * 返回一个表示失败的结果。
   *
   * @param status 状态码
   * @param <T>    返回结果的类型
   * @return 表示失败的 RestBean 对象
   */
  public static <T> RestBean<T> failure(Integer status) {
    return new RestBean<>(status, false, null);
  }

  /**
   * 返回一个表示失败的结果。
   *
   * @param status 状态码
   * @param result 返回的结果
   * @param <T>    返回结果的类型
   * @return 表示失败的 RestBean 对象
   */
  public static <T> RestBean<T> failure(Integer status, T result) {
    return new RestBean<>(status, false, result);
  }
}
