package com.wv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MonitoringApplication extends SpringBootServletInitializer {

    /** 외장톰캣 사용을 위한 오버라이딩 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MonitoringApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MonitoringApplication.class, args);
    }

}
