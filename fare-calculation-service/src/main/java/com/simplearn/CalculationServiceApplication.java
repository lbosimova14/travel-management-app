package com.simplearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculationServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(CalculationServiceApplication.class, args);

        System.out.println("""
                
                ╔════════════════════════════════════════════════════════════════╗
                ║                      Calculation SERVICE STARTED               ║
                ║                                                                ║
                ║  📝 Handles fare calculation                       ║
                ║  🔗 Communicates with booking service                          ║
                ║                                                                ║
                ║                                                                ║
                ║  Available at: http://localhost:8082/api/fare                  ║
                ╚════════════════════════════════════════════════════════════════╝
                """);
    }

}