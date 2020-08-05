package demo_6;

public class ExampleObj2 {

  // 属性是一个自定义类的类型
  private ExampleObjSub myExampleObjSub;

  // 同样需要 Setter 方法
  public void setMyExampleObjSub(ExampleObjSub myExampleObjSub) {
    this.myExampleObjSub = myExampleObjSub;
  }

  @Override
  public String toString() {
    return "ExampleObj2{" +
            "myExampleObjSub=" + myExampleObjSub +
            '}';
  }
}
