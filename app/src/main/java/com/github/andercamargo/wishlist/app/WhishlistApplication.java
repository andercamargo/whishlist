package com.github.andercamargo.wishlist.app;


import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(
        scanBasePackages = {
                "com.github.andercamargo.wishlist.app",
                "com.github.andercamargo.wishlist.data",
                "com.github.andercamargo.wishlist.domain"
        }
)
@Log4j2
public class WhishlistApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WhishlistApplication.class, args);
        log.info("Whislist foi iniciado com sucesso!");
    }
}