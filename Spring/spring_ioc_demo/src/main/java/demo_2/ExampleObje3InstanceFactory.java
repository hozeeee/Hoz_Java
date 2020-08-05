package demo_2;

public class ExampleObje3InstanceFactory {
  public ExampleObj3 exampleObj3;

  public ExampleObje3InstanceFactory() {
    exampleObj3 = new ExampleObj3();
  }

  // 需要实例化之后才能调用该方法，然后才能获取到实例对象
  public ExampleObj3 getExampleObj3() {
    return exampleObj3;
  }
}


class ExampleObj3 {
  public ExampleObj3() {
    System.out.println("ExampleObj3 实例化成功！");
  }
}
