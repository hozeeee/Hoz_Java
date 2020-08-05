package demo_5;

// 实现了接口的类
public class UserService implements IUserService {
  public void insert() {
    System.out.println("执行新增方法....");
  }

  public void delete() {
    System.out.println("执行删除方法....");
  }

  public void update() {
    System.out.println("执行更新方法....");
  }

  public void search() {
    System.out.println("执行查询方法....");
  }
}
