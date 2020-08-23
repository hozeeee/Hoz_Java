package com.example.spring_cloud_demo.my_server_1.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstTestController {

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test() {
    return "success";
  }
}
