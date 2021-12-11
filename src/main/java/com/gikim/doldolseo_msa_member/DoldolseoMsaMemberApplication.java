package com.gikim.doldolseo_msa_member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DoldolseoMsaMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoldolseoMsaMemberApplication.class, args);
    }
}
