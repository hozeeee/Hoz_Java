package demo_6;

public class ExampleObj4 {
  private String name;
  private Integer age;
  private Double random;

  @Override
  public String toString() {
    return "ExampleObj4{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", random=" + random +
            '}';
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setRandom(Double random) {
    this.random = random;
  }
}
