package demo_8;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo8Tester {
  @Test
  public void doTest() {
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_8.xml");
    ExampleObj exampleObj = (ExampleObj) cpxac.getBean("example_obj");
    exampleObj.sayHello();
  }
}
