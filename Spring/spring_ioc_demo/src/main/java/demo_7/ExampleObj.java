package demo_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Component 表示此类会被 Spring 扫描到，参数中的字符串等效于 XML 的 id
 * 一般不建议直接使用 @Component ，因为缺少语义化。通常使用以下三种"增强型"的注解：
 * @Repository —— 针对 DAO 实现类
 * @Service ————— 针对 Service 实现类
 * @Controller —— 针对 Controller 实现类
 */
@Component("my_bean_name")
public class ExampleObj {

  // 注入普通属性 （不需要提供 Setter ）
  @Value("张三")
  private String name;

  // 注入实例对象 （@Autowired自动根据类型注入）
  @Autowired
  private SubObj1 subObj1;

  // 注入实例对象 （在@Autowired的基础上，再使用@Qualifier指定需要引入的Bean的名字）
  @Autowired
  @Qualifier("my_sub_obj_2")
  private SubObj2 subObj2;

  // 注入实例对象 （等效于 @Autowired +  @Qualifier ）
  @Resource(name = "my_sub_obj_3")
  private SubObj3 subObj3;

  // 等效于 XML 的 init-method 属性
  @PostConstruct
  public void initMethod() {
    System.out.println("init-method");
  }

  // 等效于 XML 的 destroy-method 属性
  @PreDestroy
  public void destroyMethod() {
    System.out.println("destroy-method");
  }

  public void sayHello() {
    String str = "Hello.";
    if (name != null) str += " I'm " + name + ".";
    if (subObj1 != null) str += " subObj1：" + subObj1;
    if (subObj2 != null) str += " subObj2：" + subObj2;
    if (subObj3 != null) str += " subObj3：" + subObj3;
    System.out.println(str);
  }

}

// 如果使用 @Autowired 自动注入，则可以不提供名字
@Component
class SubObj1 {
  @Override
  public String toString() {
    return "SubObj1";
  }
}

@Component("my_sub_obj_2")
class SubObj2 {
  @Override
  public String toString() {
    return "SubObj2";
  }
}

@Component("my_sub_obj_3")
class SubObj3 {
  @Override
  public String toString() {
    return "SubObj3";
  }
}