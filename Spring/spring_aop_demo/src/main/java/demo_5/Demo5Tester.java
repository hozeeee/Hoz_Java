package demo_5;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext_5.xml")
public class Demo5Tester {

  @Resource(name = "car_service")
  private CarService carService;
  @Resource(name = "user_service")
  private UserService userService;

  @Test
  public void doTest() {
    carService.save();
    System.out.println("-----------------------");
    carService.find();
    System.out.println("-----------------------");
    userService.save();
    System.out.println("-----------------------");
    userService.find();
  }
}

