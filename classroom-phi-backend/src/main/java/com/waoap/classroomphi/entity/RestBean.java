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

  private String message;

  private T data;

  private RestBean(Integer status, Boolean success, String message, T data) {
    this.status = status;
    this.success = success;
    this.message = message;
    this.data = data;
  }

  /**
   * 返回一个带有消息的成功结果。
   *
   * @param message 消息
   * @param <T>     返回结果中数据的类型
   * @return 表示成功的结果
   */
  public static <T> RestBean<T> success(String message) {
    return new RestBean<>(200, true, message, null);
  }

  /**
   * 返回一个带有消息和数据的成功结果。
   *
   * @param message 消息
   * @param data    数据
   * @param <T>     返回结果中数据的类型
   * @return 表示成功的结果
   */
  public static <T> RestBean<T> success(String message, T data) {
    return new RestBean<>(200, true, message, data);
  }

  /**
   * 返回一个带有状态码和消息的失败结果。
   *
   * @param status  状态码
   * @param message 消息
   * @param <T>     返回结果中数据的类型
   * @return 表示失败的结果
   */
  public static <T> RestBean<T> failure(Integer status, String message) {
    return new RestBean<>(status, false, message, null);
  }

  /**
   * 返回一个带有状态码、消息和数据的失败结果。
   *
   * @param status  状态码
   * @param message 消息
   * @param data    数据
   * @param <T>     返回结果中数据的类型
   * @return 表示失败的结果
   */
  public static <T> RestBean<T> failure(Integer status, String message, T data) {
    return new RestBean<>(status, false, message, data);
  }
}
