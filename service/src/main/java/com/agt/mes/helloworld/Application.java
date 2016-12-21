package com.agt.mes.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
System.out.println("Going to call system.exit zzzzzzzz");
System.exit(1);
        SpringApplication.run(Application.class, args);
    }

}
