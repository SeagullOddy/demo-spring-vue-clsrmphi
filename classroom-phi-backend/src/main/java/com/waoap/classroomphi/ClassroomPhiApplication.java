package com.waoap.classroomphi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot 应用程序启动类。
 *
 * <h2>注解</h2>
 *
 * {@link SpringBootApplication} 注解结合了
 * {@link SpringBootConfiguration}、{@link EnableAutoConfiguration} 和 {@link ComponentScan}三个注解。
 *
 * <ul>
 * <li>{@link SpringBootConfiguration} 注解会将当前类标注为 SpringBoot 应用的配置类。</li>
 * <li>{@link EnableAutoConfiguration} 注解会启用自动配置，SpringBoot 会根据你添加的依赖进行自动配置。</li>
 * <li>{@link ComponentScan} 注解会自动扫描当前类所在包和子包下的组件，包括配置类、服务类等，将他们注入 IoC 容器，注册为 Bean。</li>
 * <li>SpringBoot 推荐使用 Java 代码（配置类）来实现配置（虽然也可以使用 XML）。通常，可以把 Application 启动类看作 SpringBoot 的根/核心配置类。</li>
 * </ul>
 *
 * <h2>SpringBoot 自动配置、按需自动加载机制</h2>
 *
 * SpringBoot 的各种 starter 场景启动器都依赖于基础的 spring-boot-starter，而后者添加了 spring-boot-autoconfigure 依赖，该依赖中有全场景的默认配置，会在使用对应的 starter 时自动导入对应配置，从而实现在程序启动时按需自动加载，如按需自动向 IoC 容器中注入对应的组件（如 Tomcat、SpringMVC 需要 dispatcherServlet、beanNameViewResolver、characterEncodingFilter 等组件），容器中有了对应的 Bean，就可以实现对应的功能。
 *
 * @author Waoap
 */
@SpringBootApplication
public class ClassroomPhiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClassroomPhiApplication.class, args);
  }
}
