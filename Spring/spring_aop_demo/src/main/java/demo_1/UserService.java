package demo_1;

// 只有实现于某个接口的类，才能使用 JDK 的代理
public class UserService implements IUserService {

  public void save() {
    System.out.println("执行 save 方法....");
  }

  public void find() {
    System.out.println("执行 find 方法....");
  }
}
