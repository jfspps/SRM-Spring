package com.srm.config;

import com.srm.DBlogin.ExampleDBlogin;
import com.srm.DBlogin.ExampleJMSLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

//informs Spring to link the properties file (normally found in /resources) with the new bean below
// For multiple properties files, one could also use
// @PropertySource({"classpath:database.properties", "classpath:jmsdatabase.properties"}) or delete this annotation
// entirely and list all custom properties in application.properties, along with all other Spring Boot properties
@Configuration
@PropertySources({
        @PropertySource("classpath:database.properties"),
        @PropertySource("classpath:jmsdatabase.properties")
})
public class PropertyConfig {

    // access the operating system variables from Spring (see 'edit configurations' under IntelliJ for more)
    // one can add custom variables to the IDE (under 'edit configurations') and input local and/or sensitive
    // data (passwords, urls, server names etc.)
    @Autowired
    Environment environment;

    //wire these variables to the database.properties listing
    @Value("${srmdatabase.username}")
    String username;

    @Value("${srmdatabase.password}")
    String password;

    @Value("${srmdatabase.url}")
    String url;

    @Value("${srmdatabase.jms.username}")
    String jmsusername;

    @Value("${srmdatabase.jms.password}")
    String jmspassword;

    @Value("${srmdatabase.jms.url}")
    String jmsurl;

    @Value("${srmdatabase.customMessage}")
    String msg;

    //return a bean with the wired strings from database.properties (see SrmSpringApplication)
    @Bean
    public ExampleDBlogin exampleDBlogin() {
        ExampleDBlogin exampleDBlogin = new ExampleDBlogin();
        System.out.println("Current user: " + environment.getProperty("USER"));
        exampleDBlogin.setUsername(username);
        exampleDBlogin.setPassword(password);
        exampleDBlogin.setUrl(url);
        System.out.println(msg);
        return exampleDBlogin;
    }

    @Bean
    public ExampleJMSLogin exampleJMSLogin() {
        ExampleJMSLogin exampleJMSLogin = new ExampleJMSLogin();
        System.out.println("Current language: " + environment.getProperty("LANG"));
        exampleJMSLogin.setUsername(jmsusername);
        exampleJMSLogin.setPassword(jmspassword);
        exampleJMSLogin.setUrl(jmsurl);
        System.out.println(msg);
        return exampleJMSLogin;
    }
}
