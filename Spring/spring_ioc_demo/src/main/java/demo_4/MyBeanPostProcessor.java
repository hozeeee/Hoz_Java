package demo_4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

// 所有的 Bean 都会调用这个类中的方法
public class MyBeanPostProcessor implements BeanPostProcessor {

  public Object postProcessBeforeInitialization(Object bean, String idOrName) throws BeansException {
    System.out.println("5. Bean初始化前。 " + "（" + idOrName + "）");
    // 需要把 bean 返回
    return bean;
  }

  public Object postProcessAfterInitialization(Object bean, String idOrName) throws BeansException {
    System.out.println("8. Bean初始化后。 " + "（" + idOrName + "）");
    // 需要把 bean 返回
    return bean;
  }
}
