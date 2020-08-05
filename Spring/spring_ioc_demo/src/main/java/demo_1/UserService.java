package demo_1;

public class UserService implements IUserService {
  UserService() {
  }

  UserService(int age) {
    this.age = age;
  }

  public void sayHello() {
    String str = "Hello Spring! ";
    if (name != null) str += "My name is" + name + ". ";
    if (age != null) str += "I'm " + age + " year-old";
    System.out.println(str);
  }

  // 类自己的属性(一定要提供Setter,否则配置文件无法注入依赖)
  private String name;
  private Integer age;

  public void setName(String name) {
    this.name = name;
  }
}
