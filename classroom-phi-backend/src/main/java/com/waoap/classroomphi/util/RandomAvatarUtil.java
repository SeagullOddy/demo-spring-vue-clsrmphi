package com.waoap.classroomphi.util;

import java.util.Random;

/**
 * 随机生成头像的工具类，生成的头像来源于课堂派，共40张。
 *
 * @author Waoap
 */
public class RandomAvatarUtil {

  /**
   * 随机生成头像。
   *
   * @return 头像的URL
   */
  public static String getRandomAvatar() {
    return "https://assets.ketangpai.com//Public/Common/img/40/" + new Random().nextInt(10, 40)
        + ".png";
  }
}
