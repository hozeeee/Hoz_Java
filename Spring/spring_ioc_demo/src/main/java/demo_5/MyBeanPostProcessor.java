package demo_5;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanPostProcessor implements BeanPostProcessor {
  public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
    return o;
  }

  // 在这里可以对特定对象的特定方法进行增强
  public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
    // 如果不是目标对象，不代理
    if (!"my_bean".equals(beanName)) return bean;
    // 创建调用控制器 （直接创建实现InvocationHandler接口的匿名类）
    InvocationHandler invocationHandler = new InvocationHandler() {
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("insert".equals(method.getName())) System.out.println("======= 正在权限校验 =======");
        return method.invoke(bean, args);
      }
    };
    // 创建一个代理对象 （传入：类的加载器、类的接口、调用控制器）
    Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), invocationHandler);
    return proxy;
  }
}
