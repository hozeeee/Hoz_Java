package com.example.spring_cloud_demo.my_server_1.service;

import com.example.spring_cloud_demo.server_common.entity.Person;
import com.example.spring_cloud_demo.server_common.mapper.PersonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


// 注意， @Service 注解是必须的
@Service
public class PersonService {
  // spring 的数据注入
  @Resource
  private PersonMapper personMapper;
  // 该方法提供给 controller 使用
  public List<Person> getAllPerson() {
    return personMapper.selectByExample(null);
  }

}
