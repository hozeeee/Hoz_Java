package demo_1;

import org.junit.Test;

public class Demo1Tester {

  @Test
  public void doTest() {
    // 未被代理的对象
    IUserService userService = new UserService();
    userService.save();
    userService.find();
    System.out.println("---------------------------");

    // 测试 JDK 的代理
    IUserService userService1 = (IUserService) new ProxyCreator(userService).createProxy();
    userService1.save();
    userService1.find();

  }
}
