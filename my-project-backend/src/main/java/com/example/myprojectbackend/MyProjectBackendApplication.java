package com.example.myprojectbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.myprojectbackend.mapper")
public class MyProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyProjectBackendApplication.class, args);
	}

}
