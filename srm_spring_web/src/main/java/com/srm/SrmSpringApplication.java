package com.srm;

import com.srm.DBlogin.ExampleDBlogin;
import com.srm.DBlogin.ExampleJMSLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
        //ConfigurableApplicationContext extends ApplicationContext and adds configuration methods
        ConfigurableApplicationContext context = SpringApplication.run(SrmSpringApplication.class, args);

        //load database login credentials
        ExampleDBlogin exampleDBlogin = context.getBean(ExampleDBlogin.class);
        System.out.println("Loading database credentials for " + exampleDBlogin.getUsername());
        //add other methods to establish DB connection??

        ExampleJMSLogin exampleJMSLogin = context.getBean(ExampleJMSLogin.class);
        System.out.println("Loading database credentials for " + exampleJMSLogin.getUsername());

    }

}
