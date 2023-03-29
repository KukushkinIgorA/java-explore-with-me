package ru.practicum.explorewithme.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.practicum.explorewithme.main", "ru.practicum.explorewithme.client"})
public class EwmServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(EwmServiceApp.class, args);
    }
}
