package com.example.spring_cloud_demo.my_server_1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
// 扫描指定的包，把其中的文件作为 mapper 提供给 MyBatis
@MapperScan("com.example.spring_cloud_demo.server_common.mapper")
public class MyServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(MyServer1Application.class, args);
	}

}
