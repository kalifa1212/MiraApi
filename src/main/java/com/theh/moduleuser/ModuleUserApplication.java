package com.theh.moduleuser;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.TimeZone;

@EnableWebMvc
//@EnableJdbcHttpSession
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class ModuleUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleUserApplication.class, args);
    }

//    static {
//        // Charger le fichier .env
//        Dotenv dotenv = Dotenv.load();
//
//        // Charger les variables dans le système d'environnement
//        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
//        System.setProperty("GMAIL_USERNAME", dotenv.get("GMAIL_USERNAME"));
//        System.setProperty("GMAIL_PASSWORD", dotenv.get("GMAIL_PASSWORD"));
//    }
    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+1"));
    }
}
