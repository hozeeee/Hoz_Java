package demo_3;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

// 前置通知/增强类,需要实现
public class MyBeforeAdvice implements MethodBeforeAdvice {

  // 方法内部就是"增强"的内容
  public void before(Method method, Object[] objects, Object o) throws Throwable {
    System.out.println("前置增强的内容......(" + method.getName() + "|" + objects.getClass() + ")");
  }

}
