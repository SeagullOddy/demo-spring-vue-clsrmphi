package com.waoap.classroomphi.util;

import java.util.Random;

/**
 * 随机生成图片网址的工具类，生成的图片均来源于课堂派。
 *
 * @author Waoap
 */
public class RandomImageUtil {

  /**
   * 随机生成头像，生成的头像来源于课堂派，共30张。
   *
   * @return 头像的URL
   */
  public static String getRandomAvatar() {
    return "https://assets.ketangpai.com//Public/Common/img/40/" + new Random().nextInt(10, 41)
        + ".png";
  }

  /**
   * 随机生成课程皮肤，生成的皮肤来源于课堂派，共23张。
   *
   * @return 皮肤的URL
   */
  public static String getRandomCourseSkin() {
    return "https://assets.ketangpai.com/theme/min/" + new Random().nextInt(10, 33) + ".png";
  }
}
