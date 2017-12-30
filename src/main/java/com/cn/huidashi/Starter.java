package com.cn.huidashi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Alvin Du
 */
@SpringBootApplication
@EnableWebMvc
@ServletComponentScan
public class Starter {

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Starter.class);
        app.run();

    }

}
