
# AspectJ

AspectJ 是一个基于 Java 语言的 **AOP 框架**。在没有 AspectJ 之前，我们就是使用传统的 Spring AOP 开发。

AspectJ 是一个独立于 Spring 的框架。

在 Spring2.0 之后，新增了对 AspectJ 切点表达式的支持。

`@AspectJ` 是 AspectJ1.5 新增功能，通过 JDK5 注解技术，允许直接在 Bean 类中定义切面。

在新版的 Spring 框架，建议使用 AspectJ 方式来方法 AOP 。

AspectJ 提供的通知类型比 Spring AOP 略多一点。有如下几个：

- `@Before`: 前置通知。相当于 `MethodBeforeAdvice` 。
- `@AfterReturning`: 后置通知。相当于 `AfterReturningAdvice` 。
- `@Around`: 环绕通知。相当于 `MethodInterceptor` 。
- `@AfterThrowing`: 异常抛出通知。相当于 `ThrowAdvice` 。
- `@After`: 最终通知。不管是否异常，该通知都会执行。
- `@DeclareParents`: 引介通知。相当于 `IntroductionInterceptor` (不要求掌握)。

此部分示例代码都在 [spring_aspectj_demo](./spring_aspectj_demo) 中。

</br>

## 环境准备

1. 添加依赖：

    ``` xml
    <!-- spring 4 件套 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-expression</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <!-- AOP 相关依赖 -->
    <dependency>
      <groupId>aopalliance</groupId>
      <artifactId>aopalliance</artifactId>
      <version>1.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aop</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <!-- AspectJ -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.5</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <!-- 单元测试 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>4.2.5.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
    </dependency>
    ```

2. 创建配置文件 `applicationContext.xml` ，并修改 `<beans>` 的属性：

    （可以参考 `spring-framework-4.2.4.RELEASE/docs/spring-framework-reference/html/xsd-configuration.html` ）

    ``` xml
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:aop="http://www.springframework.org/schema/aop" xsi:schemaLocation="
            http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    ```

</br>

## 使用注解开发

这部分的示例代码在 **demo_1** 包。

在 `applicationContext.xml` 配置：

``` xml
<!-- 开启 AspectJ 的注解开发 -->
<aop:aspectj-autoproxy/>

<!-- 目标类 （也可以选择注解的方式配置） -->
<bean id="user_service" class="demo_1.UserService"/>

<!-- 定义切面 -->
<bean class="demo_1.MyAspectjAnnotation"/>
```

重点是 `MyAspectjAnnotation` 的内容：

``` java
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
```

</br>

## 使用XML开发

这部分的示例代码在 **demo_2** 包。

定义切面类：

``` java
public class MyAspectjXML {
  public void before() {
    System.out.println("前置通知................");
  }
  public void afterReturning(Object obj) {
    System.out.println("后置通知............... (返回值:" + obj + ")");
  }
}
```

在 `applicationContext.xml` 的配置：

``` xml
<!-- 配置目标类 -->
<bean id="user_service" class="demo_2.UserService"/>

<!-- 配置切面类 -->
<bean id="MyAspectjXML" class="demo_2.MyAspectjXML"/>

<!-- AOP 相关的配置 -->
<aop:config>
  <!-- 配置切入点 -->
  <aop:pointcut id="pointcut1" expression="execution(* demo_2.UserService.save*())"/>
  <aop:pointcut id="pointcut2" expression="execution(* demo_2.UserService.find())"/>
  <!-- 配置切面 -->
  <aop:aspect ref="MyAspectjXML">
    <!-- 前置通知 -->
    <!-- method 指定执行的方法，需要是 MyAspectjXML 中定义的方法 -->
    <!-- pointcut-ref 指定切入点，就是上面的 xml 定义好的 -->
    <aop:before method="before" pointcut-ref="pointcut1"/>
    <!-- 后置通知 -->
    <aop:after-returning method="afterReturning" pointcut-ref="pointcut2" returning="obj"/>
  </aop:aspect>
</aop:config>
```
