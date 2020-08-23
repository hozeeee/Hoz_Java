package com.example.spring_cloud_demo.my_server_1.controller;

import com.example.spring_cloud_demo.server_common.entity.Person;
import com.example.spring_cloud_demo.my_server_1.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

// @RestController 表示是 REST_ful 风格的接口
@RestController
public class PersonController {
  @Resource
  private PersonService personService;
  @GetMapping("/getAllPerson")
  public List<Person> getAllPerson() {
    return personService.getAllPerson();
  }
}
