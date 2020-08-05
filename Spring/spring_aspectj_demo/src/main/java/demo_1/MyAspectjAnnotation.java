package demo_1;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MyAspectjAnnotation {

  // execution(<访问修饰符>?<返回类型><方法名>(<参数>)<异常>)
  // 示例：
  // execution(public * *(..))                    匹配任意包下的任意类的任意 public 方法，"(..)"表示任意参数
  // execution(* org.example.dao.*(..))           匹配指定包(不包含子包)下所有类的所有方法 （示例是dao包）
  // execution(* org.example.dao..*(..))          匹配指定包(包含子包)下所有类的所有方法 （示例是dao包）
  // execution(* org.example.dao.UserDao.*(..))   匹配指定类的所有方法 （示例是UserDao类）
  // execution(* org.example.dao.IService+.*(..)) 匹配实现指定接口的所有类的所有方法 （示例是实现IService接口的所有类）
  // execution(* save*(..))                       匹配所有save开头的方法

  // 针对 UserService包下的 以save开头的 任意参数的 方法
  @Before(value = "execution(* demo_1.UserService.save*(..))")
  // 前置通知方法还有一个 JoinPoint 的参数可用，用于获取切点信息
  public void beforeInterceptor(JoinPoint joinPoint) {
    System.out.println("前置通知...... (" + joinPoint + ")");
  }

  // value 的配置跟上面一样
  // 后置通知是可以获取方法的返回值，通过 returning 属性设置返回值的变量名
  @AfterReturning(value = "execution(* demo_1.UserService.update(..)))", returning = "result")
  // 方法中的 result 需要与 returning 设置相同，代表方法的返回值
  public void afterReturning(Object result) {
    System.out.println("后置通知...... (方法返回值: " + result + ")");
  }

  @Around(value = "execution(* demo_1.UserService.find(..)))")
  public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("环绕前通知..............");
    // 执行目标方法 （若不调用，则不执行）
    Object obj = joinPoint.proceed();
    System.out.println("环绕后通知..............");
    return obj;
  }

  @AfterThrowing(value = "execution(* demo_1.UserService.delete(..)))", throwing = "err")
  // 跟上面类似，通过 returning 属性设置错误对象的变量名
  public void afterThrowing(Throwable err) {
    System.out.println("异常抛出通知.......... (异常:" + err + ")");
  }

  @After(value = "execution(* demo_1.UserService.delete2(..)))")
  public void after() {
    System.out.println("最终通知..........");
  }


  // ---------------------------------------------------------------------------------------------
  // 其实建议的做法是，使用 @Pointcut 定义匹配规则，通知注解指定该方法。如下：

  // 写法跟上面类似，只是传入方法 （多个方法用"||"分隔）
  @Before(value = "test1()||test2()")
  public void before2() {
    System.out.println("使用 @Pointcut 的前置通知 .......");
  }
  @AfterReturning(value = "test3()", returning = "result")
  public void afterReturning2(Object result) {
    System.out.println("使用 @Pointcut 的后置通知 ....... (返回值:" + result + ")");
  }

  // 定义匹配规则，方法名可以随意起 （由于不会被其他地方调用，修饰符使用private即可）
  @Pointcut(value = "execution(* demo_1.UserService.test1(..))")
  private void test1() { }
  @Pointcut(value = "execution(* demo_1.UserService.test2(..))")
  private void test2() { }
  @Pointcut(value = "execution(* demo_1.UserService.test3(..))")
  private void test3() { }
}
