package com.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /*配合前端vue使用非哈希模式的路由
    * SpringBoot默认匹配不到URL时，会返回一个默认的页面，所以可以配置一下默认的配置Bean。
    * 这个页面就是你 app 依赖的页面，为index.html*/
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
//                factory.setPort(8081);
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
                Set<ErrorPage> errorPages = new HashSet<>();
                errorPages.add(error404Page);
                factory.setErrorPages(errorPages);
            }
        };
    }

}
