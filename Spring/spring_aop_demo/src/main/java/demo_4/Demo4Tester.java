package demo_4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_4.xml")
public class Demo4Tester {

  @Resource(name = "my_proxy_obj")
  private UserService userService;

  @Test
  public void doTest() {
    userService.save();
    System.out.println("-----------------------");
    userService.save2();
    System.out.println("-----------------------");
    userService.find();
  }
}
