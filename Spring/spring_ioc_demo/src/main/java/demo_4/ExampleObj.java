package demo_4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// 演示完整的 Bean 生命周期
public class ExampleObj implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

  public ExampleObj() {
    System.out.println("1. 对象初始化");
  }

  private String myValue;

  public void setMyValue(String myValue) {
    this.myValue = myValue;
    System.out.println("2. 设置属性。");
  }

  public void setBeanName(String idOrName) {
    System.out.println("3. 设置Bean的名称。 " + "（" + idOrName + "）");
  }

  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("4. 了解工厂信息_1。 " + beanFactory);
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("4. 了解工厂信息_2。 " + applicationContext);
  }

  public void afterPropertiesSet() throws Exception {
    System.out.println("6. 属性设置后。");
  }

  public void initMethod() {
    System.out.println("7. init-method 。");
  }

  public void run() {
    System.out.println("9. 执行业务代码。");
  }

  public void destroy() throws Exception {
    System.out.println("10. 执行Spring的销毁方法。");
  }

  public void destroyMethod() {
    System.out.println("11. destroy-method 。");
  }
}
