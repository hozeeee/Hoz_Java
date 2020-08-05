package demo_1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyCreator implements InvocationHandler {
  // 被代理的对象
  private Object target;

  ProxyCreator(Object target) {
    this.target = target;
  }

  // 返回代理对象
  public Object createProxy() {
    // 第三个参数是 InvocationHandler 的实现，此类实现了该接口，所以直接传入 this
    Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    return proxy;
  }

  // InvocationHandler 接口中规定的方法
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 判断方法是否为"切入点"，例子中仅对"save"方法增强
    if ("save".equals(method.getName())) {
      System.out.println("正在对 save 方法鉴权....");
    }
    // 返回被织入后的对象
    return method.invoke(target, args);
  }
}
