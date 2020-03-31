package com.vjurkov.courseenrollment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class NullConfig {

    //null pattern
    @Bean
    public File nullImage(){
        try {
            return ResourceUtils.getFile("classpath:nothing.png");
        } catch (FileNotFoundException e) {
            return new File("stub.png");
        }
    }
}
