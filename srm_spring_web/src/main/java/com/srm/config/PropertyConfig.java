package com.srm.config;

import com.srm.DBlogin.ExampleDBlogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//informs Spring to link the properties file (normally found in /resources) with the new bean below
@Configuration
@PropertySource("classpath:database.properties")
public class PropertyConfig {

    //wire these variables to the database.properties listing
    @Value("${srmdatabase.username}")
    String username;

    @Value("${srmdatabase.password}")
    String password;

    @Value("${srmdatabase.url}")
    String url;

    //return a bean with the wired strings from database.properties (see SrmSpringApplication)
    @Bean
    public ExampleDBlogin exampleDBlogin() {
        ExampleDBlogin exampleDBlogin = new ExampleDBlogin();
        exampleDBlogin.setUsername(username);
        exampleDBlogin.setPassword(password);
        exampleDBlogin.setUrl(url);
        return exampleDBlogin;
    }

    // this is an alternative implementation to @PropertySource annotation
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties(){
//        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
//        return propertySourcesPlaceholderConfigurer;
//    }
}
