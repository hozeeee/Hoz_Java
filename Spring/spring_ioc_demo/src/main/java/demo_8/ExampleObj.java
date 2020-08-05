package demo_8;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class ExampleObj {

  @Resource(name = "sub_obj_1")
  private SubObj1 subObj1;
  @Resource(name = "sub_obj_2")
  private SubObj2 subObj2;

  public void sayHello() {
    String str = "Hello! ";
    if (subObj1 != null) str += subObj1.getRandom() + " ";
    if (subObj2 != null) str += subObj2.getName() + " ";
    System.out.println(str);
  }
}

class SubObj1 {
  public double getRandom() {
    return Math.random();
  }
}

class SubObj2 {
  public String getName() {
    return "张三";
  }
}
