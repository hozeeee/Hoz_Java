package demo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import demo.entity.User;
import demo.entity.UserRequestParams;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/test2")
public class Controller3 {

  @GetMapping("/get")
  // 参数中与请求的参数对应 （数据类型会被自动转换，若类型转换失败，会响应400错误）
  public String myGet(String username, Long password) {
    System.out.println("username: " + username + "; password: " + password);
    return "get success";
  }


  // 默认只能处理 application/x-www-form-urlencoded 类型的
  @PostMapping("/post")
  // 假如前端提供的参数与需要指定的字段不对应，可以手动设置映射关系。下示例是将 user_name 映射到 userName 变量中
  public String myPost(@RequestParam("user_name") String userName, String password) {
    System.out.println("userName: " + userName + "; password: " + password);
    return "post success";
  }


  @GetMapping("/getuser")
  // 同名变量会自动"塞"到类中的同名属性上，如 userName 会被赋值到 user.userName
  // 还有一点要注意， user.userName 和 userName 都会被赋值
  public String getUser(UserRequestParams user, String userName) {
    System.out.println(user.toString() + userName);
    return "get success";
  }


  // 如果需要接收日期参数，使用 @DateTimeFormat(pattern="yyyy-MM-dd") 注解
  @GetMapping("/date")
  public String testDate(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date) {
    System.out.println(date);
    return "get success";
  }


  // 路径参数 （${id}定义路径变量的名字）
  @GetMapping("/userId/${id}")
  // @PathVariable() 获取特定的路径变量，然后赋值给指定变量
  public String testPathVariable(@PathVariable("id") String uid) {
    System.out.println("userId" + uid);
    return "success";
  }


  // JSON 序列化 （过程是自动的，被返回的对象会自动转化成 JSON 字符串）
  @GetMapping("/json")
  public ExampleObj getJson() {
    return new ExampleObj();
  }


  // 测试局部 CORS
  @PostMapping("/cors")
  // @CrossOrigin 表示该请求可以跨域。 origins 指定可访问的域; maxAge 表示非简单请求的预检请求的有效时间(单位秒)
  @CrossOrigin(origins = "*", maxAge = 3600)
  public String testCors() {
    return "{msg:\"success\"}";
  }

  // 测试局部 CORS （此部分配置在applicationContext.xml中）
  @PostMapping("/cors2")
  public String testCors2() {
    return "{msg:\"success\"}";
  }


  // 测试拦截器
  @GetMapping("/interceptor")
  public String testInterceptor() {
    return "success";
  }


  // 测试 ModelAndView
  @GetMapping("/view")
  public ModelAndView getView() {
    // 指定模板文件创建 ModelAndView 对象
    ModelAndView mav = new ModelAndView("/view.jsp");
//    ExampleObj obj = new ExampleObj();
    User user = new User();
    user.setName("张三");
    user.setAge(18);
    mav.addObject("u", user);
    return mav;
  }

}


class ExampleObj {
  public String name = "张三";
  public Integer age = 18;
  public Double num = 3.1415926d;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date date = new Date();
}
