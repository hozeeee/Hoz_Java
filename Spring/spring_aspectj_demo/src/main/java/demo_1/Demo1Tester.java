package demo_1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Demo1Tester {

  @Resource(name = "user_service")
  private UserService userService;

  @Test
  public void doTest() {
    userService.save();
    System.out.println("-----------------------");
    userService.save2();
    System.out.println("-----------------------");
    userService.update();
    System.out.println("-----------------------");
    userService.find();
    System.out.println("-----------------------");
    try {
      userService.delete();
    } catch (Exception e) {
      //
    }
    System.out.println("-----------------------");
    try {
      userService.delete2();
    } catch (Exception e) {
      //
    }
    System.out.println("-----------------------");
    userService.test1();
    System.out.println("-----------------------");
    userService.test2();
    System.out.println("-----------------------");
    userService.test3();
    System.out.println("-----------------------");
  }
}
