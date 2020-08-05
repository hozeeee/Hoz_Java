package demo_6;

// 普通属性输入
public class ExampleObj1 {
  private String name;

  // 必须提供 Setter ，否则无法注入
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ExampleObj1{" +
            "name='" + name + '\'' +
            '}';
  }
}
