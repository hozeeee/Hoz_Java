package demo_2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo2Tester {

  @Test
  public void doTest() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext_2.xml");
    // 需要注意的是，在上一步导入配置文件的时候，已经把配置文件中指定的类实例化，即使把下面的代码注释掉
    ExampleObj1 exampleObj1 = (ExampleObj1) ac.getBean("bean_demo_1");
    ExampleObj2 exampleObj2 = (ExampleObj2) ac.getBean("bean_demo_2");
    ExampleObj3 exampleObj3 = (ExampleObj3) ac.getBean("bean_demo_3");

  }

}
