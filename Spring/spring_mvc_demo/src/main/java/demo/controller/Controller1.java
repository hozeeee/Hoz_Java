package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller 表示它是一个控制器
@Controller
public class Controller1 {

  // @GetMapping 表示 Get 方法，并指定路径
  @GetMapping("/testget")
  // @ResponseBody 表示该方法的返回值作为响应体
  @ResponseBody
  public String testGet() {
    return "success response";
  }
}
