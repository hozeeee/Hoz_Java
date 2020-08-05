package demo_1;

public class UserService {
  public void save() {
    System.out.println("正在执行 UserService 的 save 方法 ....");
  }

  public void save2() {
    System.out.println("正在执行 UserService 的 save2 方法 ....");
  }

  public void find() {
    System.out.println("正在执行 UserService 的 find 方法 ....");
  }

  // 注意！这是一个有返回值的方法
  public double update() {
    System.out.println("正在执行 UserService 的 update 方法 ....");
    return 3.1415926d;
  }

  // 注意！这是一个抛出异常的方法
  public void delete() throws Exception {
    System.out.println("正在执行 UserService 的 delete 方法 ....");
    throw new Exception("???");
  }

  // 注意！这还一个抛出异常的方法
  public void delete2() throws Exception {
    System.out.println("正在执行 UserService 的 delete2 方法 ....");
    throw new Exception("!!!");
  }


  public void test1() {
    System.out.println("正在执行 UserService 的 test1 方法 ....");
  }

  public void test2() {
    System.out.println("正在执行 UserService 的 test2 方法 ....");
  }

  public int test3() {
    System.out.println("正在执行 UserService 的 test3 方法 ....");
    return 1;
  }

}
