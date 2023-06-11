package com.waoap.classroomphi.mapper;

import com.waoap.classroomphi.entity.Account;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

/**
 * 账号的 Mapper 接口。
 *
 * <h2>备注</h2>
 *
 * 在使用 MyBatis 框架时，可以使用注解来配置 MyBatis 中的对象关系映射，需要给接口添加 {@link Mapper} 注解，告知 MyBatis 这是一个 Mapper 接口（一个
 * Mapper 接口相当于一个 mapper.xml）。而后在接口中添加所需的方法，并且给方法添加所需的 {@link Select} 等注解来配置该方法对应的 SQL
 * 语句、{@link Results} 等注解来配置映射关系、{@link ConstructorArgs} 等注解来配置映射时使用的构造方法的参数列表。假设下文中出现的 Test 类都是只有
 * id、name、age 三个属性，且分别为 Integer、String、String 类型，下文的代码给出了一个 Mapper 接口的示例：
 *
 * <pre>
 * &#64;Mapper
 * public interface TestMapper {
 *
 *   &#64;Select("SELECT * FROM test WHERE id = #{id}")
 *   &#64;Results({
 *     &#64;Result(id = true, column = "id", property = "id"),
 *     &#64;Result(column = "name", property = "name"),
 *     &#64;Result(column = "age", property = "age"),
 *   })
 *   Test findTestById(Integer id);
 * }
 * </pre>
 *
 * 如果方法接受的参数只有一个，那么在 SQL 语句中可以如上面的代码一样直接使用参数名来引用参数，且如果参数是一个对象，那么甚至可以直接使用对象的属性名来引用对象的属性：
 *
 * <pre>
 *   &#64;Select("SELECT * FROM test WHERE id = #{id}")
 *   Test findTestById(Test test);
 * </pre>
 *
 * 如果方法接受的参数有多个，那么在 SQL 语句中需要使用 {@link Param} 注解配置对应关系，例如：
 *
 * <pre>
 *   &#64;Select("SELECT * FROM test WHERE id = #{id} AND name = #{name}")
 *   Test findTestByIdAndName(&#64;Param Integer id, &#64;Param String name);
 * </pre>
 *
 * 或者：
 *
 * <pre>
 *   &#64;Select("SELECT * FROM test WHERE id = #{id} AND name = #{name}")
 *   Test findTestByIdAndName(&#64;Param("id") Integer id, &#64;Param("name") String name);
 * </pre>
 *
 * 如果多参数且其中存在参数是对象，那么引用对象的属性应使用 `参数.属性名` 的格式：
 *
 * <pre>
 *   &#64;Select("SELECT * FROM test WHERE id = #{id} AND name = #{test.name}")
 *   Test findTestByIdAndName(&#64;Param Integer id, &#64;Param Test test);
 * </pre>
 *
 * 使用注解开发实际更像是将 mapper.xml 抽象化成了 Mapper 接口，因此理解了 XML 配置文件的格式后，可以举一反三地理解注解的配置方式，例如使用
 * {@link CacheNamespace} 犹如在 mapper.xml 中使用 cache 标签一样，可以给 Mapper 配置二级缓存。
 *
 * <h2>优点</h2>
 *
 * <ul>
 * <li>在简单业务逻辑的情况下，可以使代码更加简洁，减少繁琐的 XML 配置</li>
 * </ul>
 *
 * @author Waoap
 * @see Account
 */
@Mapper
public interface AccountMapper {

  /**
   * 根据姓名或邮箱查询账号
   *
   * @param usernameOrEmail 姓名或邮箱
   * @return 账号
   */
  @Select("SELECT * FROM account WHERE username = #{usernameOrEmail} OR email = #{usernameOrEmail}")
  Account findAccountByUsernameOrEmail(String usernameOrEmail);
}
