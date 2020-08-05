package demo_6;

// 示范构造函数的参数注入
public class ExampleObj3 {
  private Integer age;

  ExampleObj3(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "ExampleObj3{" +
            "age=" + age +
            '}';
  }
}
