package demo_4;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo4Tester {

  @Test
  public void doTest() {
    ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("applicationContext_4.xml");
    ExampleObj eo = (ExampleObj) cpxac.getBean("full_live_hock");

    eo.run();

    cpxac.close();
  }
}
