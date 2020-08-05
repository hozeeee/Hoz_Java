package demo_6;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo6Tester {

  @Test
  public void doTest() {
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_6.xml");

    ExampleObj1 exampleObj1 = (ExampleObj1) cpxac.getBean("inject_property");
    System.out.println(exampleObj1);
    System.out.println("-------------------------------------");

    ExampleObj2 exampleObj2 = (ExampleObj2) cpxac.getBean("inject_property_2");
    System.out.println(exampleObj2);
    System.out.println("-------------------------------------");

    ExampleObj3 exampleObj3 = (ExampleObj3) cpxac.getBean("inject_constructor_arg");
    System.out.println(exampleObj3);
    System.out.println("-------------------------------------");

    // 测试 p 命名空间的属性注入
    ExampleObj1 exampleObj1_2 = (ExampleObj1) cpxac.getBean("inject_property_p_namespace");
    ExampleObj2 exampleObj2_2 = (ExampleObj2) cpxac.getBean("inject_property_2_p_namespace");
    System.out.println(exampleObj1_2);
    System.out.println(exampleObj2_2);
    System.out.println("-------------------------------------");

    // 测试 SpEL 的属性注入
    ExampleObj4 exampleObj4 = (ExampleObj4) cpxac.getBean("inject_property_spel");
    System.out.println(exampleObj4);
    System.out.println("-------------------------------------");

    // 复杂类型的属性注入
    ExampleObj5 exampleObj5 = (ExampleObj5) cpxac.getBean("inject_property_other");
    System.out.println(exampleObj5);
    System.out.println("-------------------------------------");

  }
}
