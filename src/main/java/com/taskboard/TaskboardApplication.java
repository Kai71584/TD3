package com.taskboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot. - @SpringBootApplication active l'auto-configuration
 * et le scan des composants dans le package com.taskboard (controllers, services, repositories...).
 */
@SpringBootApplication
public class TaskboardApplication {
  public static void main(String[] args) {
    SpringApplication.run(TaskboardApplication.class, args);
  }
}
