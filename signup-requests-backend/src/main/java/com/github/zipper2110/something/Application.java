package com.github.zipper2110.something;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/*
 * Создал обычное spring boot - приложение
 */

@SpringBootApplication
@EnableRetry
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
