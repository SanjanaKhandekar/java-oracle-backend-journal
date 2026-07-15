// Day 20: Protecting controller endpoints using PreAuthorize annotations
// Topic: Spring Method-Level Security Configurations & RBAC

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@Configuration
@EnableMethodSecurity // Activates method-level role protection across the application
@RestController
@RequestMapping("/api/v1/admin-portal")
public class AdminController {

    // Restricts entry exclusively to users with the 'ADMIN' role
    @GetMapping("/dashboard")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public String getAdminDashboard() {
        return "Welcome to the Secure Admin Console. Sensitive enterprise metrics loaded.";
    }

    // Allows entry to both 'ADMIN' and 'MANAGER' roles while blocking standard 'USER' accounts
    @GetMapping("/reports")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public String getManagementReports() {
        return "Manager-level and Admin-level data reports fetched successfully.";
    }
}

// Benefit: Decentralizes security rules. Instead of managing a massive, complex, centralized 
// configuration file mapping paths to roles, developers can apply security rules directly 
// above the target controller method, making code reviews highly intuitive.
