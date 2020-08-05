package demo_2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_2.xml")
public class Demo2Tester {
  @Resource(name = "user_service")
  private UserService userService;

  @Test
  public void doTest() {
    userService.find();
    System.out.println("------------------------");
    userService.save();
    System.out.println("------------------------");
    userService.save2();
    System.out.println("------------------------");
  }

}
