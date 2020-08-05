package demo_5;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo5Tester {

  @Test
  public void doTest() {
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_5.xml");
    // 注意！这里转换的类型必须是接口！
    IUserService userService = (IUserService) cpxac.getBean("my_bean");
    // 调用该类的方法
    userService.delete();
    userService.update();
    userService.search();
    userService.insert();

    cpxac.close();
  }
}
