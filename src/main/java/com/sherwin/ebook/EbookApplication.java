package com.sherwin.ebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class EbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookApplication.class, args);
    }

}
