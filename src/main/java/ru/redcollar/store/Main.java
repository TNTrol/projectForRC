package ru.redcollar.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = "com.demo.mapper")
public class Main{

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}