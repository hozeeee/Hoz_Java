package demo_1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Demo1Tester {

  @Test
  // 对于传统的开发方式，是直接 new 一个对象，然后使用它
  public void tradition() {
    IUserService userService = new UserService();
    userService.sayHello();
  }

  @Test
  // 使用 Spring 的方式
  public void useSpring() {
    // 指定配置文件,创建 Spring 的工厂
    // 假如配置文件不在当前项目下,可以使用 FileSystemXmlApplicationContext 替代 ClassPathXmlApplicationContext
    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    // 通过工厂获取类
    IUserService userService = (IUserService) ac.getBean("userService");
    userService.sayHello();
    //
//    System.out.println(userService.name);
  }


//  @Test
  // 通常情况下,除了实现接口的内容,还需要添加自己的属性
}
