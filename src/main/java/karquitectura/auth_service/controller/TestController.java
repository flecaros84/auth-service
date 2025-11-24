package karquitectura.auth_service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "Acceso permitido con token JWT válido";
    }
}
