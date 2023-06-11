package com.waoap.classroomphi.entity;

import lombok.Data;

/**
 * RestBean 类，用于封装后端接口的返回值，方便构造为 JSON 格式。
 *
 * @author Waoap
 */
@Data
public class RestBean<T> {

  private Integer status;

  private Boolean success;

  private T message;

  private RestBean(Integer status, Boolean success, T message) {
    this.status = status;
    this.success = success;
    this.message = message;
  }

  /**
   * 构造一个带有消息或返回值的，表示操作成功的 RestBean 对象。
   *
   * @param <T> 消息或返回值的类型
   * @return 带有消息或返回值的，表示操作成功的 RestBean 对象
   */
  public static <T> RestBean<T> success() {
    return new RestBean<>(200, true, null);
  }

  /**
   * 构造一个带有消息或返回值的，表示操作成功的 RestBean 对象。
   *
   * @param data 消息或返回值
   * @param <T>  消息或返回值的类型
   * @return 带有消息或返回值的，表示操作成功的 RestBean 对象
   */
  public static <T> RestBean<T> success(T data) {
    return new RestBean<>(200, true, data);
  }

  /**
   * 构造一个带有消息或返回值的，表示操作失败的 RestBean 对象。
   *
   * @param status 状态码
   * @param <T>    消息或返回值的类型
   * @return 带有消息或返回值的，表示操作失败的 RestBean 对象
   */
  public static <T> RestBean<T> failure(Integer status) {
    return new RestBean<>(status, false, null);
  }

  /**
   * 构造一个带有消息或返回值的，表示操作失败的 RestBean 对象。
   *
   * @param status 状态码
   * @param data   消息或返回值
   * @param <T>    消息或返回值的类型
   * @return 带有消息或返回值的，表示操作失败的 RestBean 对象
   */
  public static <T> RestBean<T> failure(Integer status, T data) {
    return new RestBean<>(status, false, data);
  }
}
