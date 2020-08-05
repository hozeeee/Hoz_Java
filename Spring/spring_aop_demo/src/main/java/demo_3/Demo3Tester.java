package demo_3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_3.xml")
public class Demo3Tester {

  // 未增强的对象
  @Resource(name = "target_obj")
  private IUserService userService;

  // 已增强的对象
  @Resource(name = "user_service_proxy")
  private IUserService userService1;

  @Test
  public void doTest() {
    userService.save();
    userService.find();
    System.out.println("-----------------------");
    userService1.save();
    userService1.find();
  }

}
