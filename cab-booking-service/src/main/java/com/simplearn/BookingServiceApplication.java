package com.simplearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BookingServiceApplication {
    public static void main(String[] args) {


        SpringApplication.run(BookingServiceApplication.class, args);

        System.out.println("""
            
            ╔═══════════════════════════════════════════════════════════════╗
            ║                      Booking SERVICE STARTED                  ║
            ║                                                               ║
            ║  📝 Handles cab booking creation and management               ║
            ║  🔗 Communicates with fare-calculation service                ║
            ║  💾 Uses in-memory storage for simplicity                     ║
            ║                                                               ║
            ║  Available at: http://localhost:8081/api/bookings             ║
            ╚═══════════════════════════════════════════════════════════════╝
            """);
    }

    /**
     * RestTemplate bean with @LoadBalanced annotation.
     *
     * @LoadBalanced enables this RestTemplate to resolve service names (e.g., "http://fare-service")
     * using Eureka service discovery. This allows dynamic resolution of service instances at runtime,
     * so you do not need to hardcode hostnames or ports. Instead, you use the logical service name
     * registered with Eureka, and Spring Cloud LoadBalancer will route requests to available instances.
     */
    @Bean
    @LoadBalanced // Enables service discovery - uses service names like "fare-service" instead of hardcoded URLs
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
