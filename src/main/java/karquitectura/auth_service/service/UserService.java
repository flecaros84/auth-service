package karquitectura.auth_service.service;

import karquitectura.auth_service.dto.AuthResponse;
import karquitectura.auth_service.dto.LoginRequest;
import karquitectura.auth_service.dto.RegisterRequest;
import karquitectura.auth_service.entity.User;
import karquitectura.auth_service.repository.UserRepository;
import karquitectura.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public User register(RegisterRequest req) {

        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("El correo ya existe");
        }

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPass()));
        u.setRole(req.getRole());

        return repo.save(u);
    }

    public AuthResponse login(LoginRequest req) {

        // 1. Crear request de autenticación
        Authentication authRequest =
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPass());

        // 2. AuthenticationManager valida el usuario
        Authentication authResult = authManager.authenticate(authRequest);

        // 3. Obtener el usuario autenticado desde la BD
        UserDetails user = (UserDetails) authResult.getPrincipal();

        // 4. Generar el JWT
        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
