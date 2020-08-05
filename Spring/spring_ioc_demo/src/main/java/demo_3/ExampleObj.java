package demo_3;

// 普通类
public class ExampleObj {
  public ExampleObj() {
    System.out.println("ExampleObj 实例化");
  }

  public void beanInit() {
    System.out.println("ExampleObj init");
  }

  public void beanDestroy() {
    System.out.println("ExampleObj destroy");
  }

}
