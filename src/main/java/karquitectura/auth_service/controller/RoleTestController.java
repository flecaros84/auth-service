package karquitectura.auth_service.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role-test")
public class RoleTestController {

    // Solo ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminOnly() {
        return "Acceso permitido SOLO a ADMIN";
    }

    // USER o ADMIN
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/user")
    public String userOrAdmin() {
        return "Acceso permitido a USER o ADMIN";
    }
}