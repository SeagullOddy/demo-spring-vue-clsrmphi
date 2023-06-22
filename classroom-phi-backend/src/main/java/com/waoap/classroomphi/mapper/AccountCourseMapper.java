package com.waoap.classroomphi.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Waoap
 */
@Mapper
public interface AccountCourseMapper {

  /**
   * 根据账户 id 和关系查询对应的课程 id 列表
   *
   * @param accountId    账户 id
   * @param relationship 关系
   * @return 课程 id 列表
   */
  @Select("SELECT course_id FROM account_course WHERE account_id = #{accountId} AND relationship = '#{relationship}'")
  List<Long> findCourseIdsByRelationShip(Long accountId, String relationship);

  /**
   * 根据账户 id 和查询置顶的课程 id 列表
   *
   * @param accountId 账户 id
   * @return 置顶的课程 id 列表
   */
  @Select("SELECT course_id FROM account_course WHERE account_id = #{accountId} AND pinned = 1")
  List<Long> findPinnedCourseIds(Long accountId);
}
