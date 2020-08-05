package demo_7;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo7Tester {

  @Test
  public void doTest() {
    // 同样需要创建工厂类，加载配置文件
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_7.xml");
    ExampleObj exampleObj = (ExampleObj) cpxac.getBean("my_bean_name");
    exampleObj.sayHello();
    cpxac.close();
  }
}
