package com.srm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * +@SpringBootApplication annotation directs Spring to scan all code and resources under the com.srm package
 * (looking for @Controller, @Service etc.). @SpringBootApplication includes the directions of other annotations:
 * +@Configuration (declares class as a Spring config)
 * +@EnableAutoConfiguration (which, among other uses, can be used to exclude components from the default selection)
 * +@ComponentScan (the annotation which specifically directs Spring to scan this package and all child - packages for code)
 */

// use @ComponentScan(basePackages={...}) to list external packages and override Spring Boot's scanning behaviour
@SpringBootApplication
public class SrmSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SrmSpringApplication.class, args);
    }

}
