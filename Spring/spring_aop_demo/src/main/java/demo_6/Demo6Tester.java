package demo_6;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_6.xml")
public class Demo6Tester {
  @Resource(name = "user_service")
  private UserService userService;
  @Resource(name = "car_service")
  private CarService carService;

  @Test
  public void doTest() {
    userService.save();
    System.out.println("-----------------------");
    userService.find();
    System.out.println("-----------------------");
    carService.save();
    System.out.println("-----------------------");
    carService.find();
  }
}
