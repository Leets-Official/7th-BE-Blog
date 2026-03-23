//    package com.leets.blog;
//
//    import org.springframework.boot.SpringApplication;
//    import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//    @SpringBootApplication
//    public class Application {
//
//        public static void main(String[] args) {
//            SpringApplication.run(Application.class, args);
//        }
//
//    }

// DB 연결 잠시 OFF
package com.leets.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration; // 1. import 추가

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 2. exclude 추가
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}