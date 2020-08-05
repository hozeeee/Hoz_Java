package demo_2;

import org.junit.Test;

public class Demo2Tester {
  @Test
  public void doTest() {
    UserService userService = new UserService();
    UserService userService1 = (UserService) new ProxyCreator(userService).createProxy();
    userService1.save();
    userService1.find();
  }
}
