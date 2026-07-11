// Day 15: Global Cross-Origin Resource Sharing (CORS) security whitelist policy setup
// Topic: Spring Security & Decoupled Full-Stack API Integration

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/**") // Applies policy to all API endpoints
                        .allowedOrigins("http://localhost:3000") // Whitelists your local React development server
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permitted HTTP actions
                        .allowedHeaders("*") // Allows all headers (Authorization, Content-Type, etc.)
                        .allowCredentials(true); // Permits sharing of secure browser cookies/session IDs
            }
        };
    }
}

// Benefit: Prevents browsers from throwing the notorious "CORS error" when your 
// frontend (port 3000) tries to fetch data from your backend API (port 8080).
