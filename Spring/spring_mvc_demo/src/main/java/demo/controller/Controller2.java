package demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
// @RequestMapping 一般用于"类"上，指定里面的方法的根路径，例如下面第一个GET方法的路径就是"/test1/get"
@RequestMapping("/test1")
public class Controller2 {

  // Get 方法的请求处理
  @GetMapping("/get")
  @ResponseBody
  public String myGet() {
    return "GET1";
  }

  // Get 方法的请求处理2 （这里使用了@RequestMapping，这种写法等效于直接写@GetMapping）
  @RequestMapping(value = "/get2", method = RequestMethod.GET)
  @ResponseBody
  public String myGet2() {
    return "GET2";
  }

  // Post 方法的请求处理
  @PostMapping("/post")
  @ResponseBody
  public String myPost() {
    return "POST";
  }

  // 通用的请求处理（不限定请求方法）
  @RequestMapping("/general")
  @ResponseBody
  public String myGeneral() {
    return "GENERAL";
  }
}
