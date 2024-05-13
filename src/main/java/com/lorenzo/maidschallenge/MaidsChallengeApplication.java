package com.lorenzo.maidschallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MaidsChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaidsChallengeApplication.class, args);
    }

}
