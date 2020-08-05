package demo_3;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo3Tester {

  @Test
  public void doTest() {
    // 创建 ClassPathXmlApplicationContext 的实现，因为 ApplicationContext 接口没有 close 方法
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_3.xml");
    ExampleObj exampleObj = (ExampleObj) cpxac.getBean("bean_live_hook");
    // 销毁 Bean
    cpxac.close();

  }

}
