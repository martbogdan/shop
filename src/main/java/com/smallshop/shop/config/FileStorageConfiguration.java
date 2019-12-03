package com.smallshop.shop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class FileStorageConfiguration {

    @Bean(name = "basePath")
    public String basePath () {
        File baseDir = new File("uploads");
        if (!baseDir.exists()){
            baseDir.mkdir();
        }
        log.info("BasePath created: ", baseDir.getAbsolutePath());
        return baseDir.getAbsolutePath();
    }

    @Bean(name= "productPath")
    public String productPuth () {
        File baseDir = new File("uploads/products");
        if (!baseDir.exists()){
            baseDir.mkdir();
        }
        log.info("ProductPath created: ", baseDir.getAbsolutePath());
        return baseDir.getAbsolutePath();
    }
}
