package demo_2;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyCreator implements MethodInterceptor {
  // 被代理的类
  private Object target;

  ProxyCreator(Object target) {
    this.target = target;
  }

  public Object createProxy() {
    // 1. 创建核心类
    Enhancer enhancer = new Enhancer();
    // 2. 设置父类
    enhancer.setSuperclass(target.getClass());
    // 3. 设置回调
    enhancer.setCallback(this);
    // 4. 生成代理
    Object proxy = enhancer.create();
    return proxy;
  }

  // 实现 MethodInterceptor 接口中的方法
  public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    // 这里跟 JDK 的代理类似
    if ("save".equals(method.getName())) {
      System.out.println("正在对 save 方法鉴权....");
    }
    return methodProxy.invokeSuper(proxy, args);
  }
}
