package demo_2;

public class ExampleObj2StaticFactory {
  static ExampleObj2 exampleObj2;

  static {
    exampleObj2 = new ExampleObj2();
  }

  // 静态方法，访问静态实例对象
  public static ExampleObj2 getExampleObj2() {
    return exampleObj2;
  }
}

class ExampleObj2 {
  public ExampleObj2() {
    System.out.println("ExampleObj2 实例化成功！");
  }
}
