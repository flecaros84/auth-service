package karquitectura.auth_service.controller;

import karquitectura.auth_service.entity.User;
import karquitectura.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserAdminController {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> listAll() {
        return repo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User u) {
        if (u.getPassword() == null || u.getPassword().isBlank()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        u.setId(null); // aseguramos que sea create
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        if (u.getRole() == null || u.getRole().isBlank()) {
            u.setRole("usuario");
        }
        if (u.getActive() == null) {
            u.setActive(true);
        }

        return repo.save(u);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u) {
        User original = repo.findById(id).orElseThrow();

        original.setName(u.getName());
        original.setEmail(u.getEmail());

        if (u.getPassword() != null && !u.getPassword().isBlank()) {
            original.setPassword(passwordEncoder.encode(u.getPassword()));
        }

        if (u.getRole() != null && !u.getRole().isBlank()) {
            original.setRole(u.getRole());
        }

        if (u.getActive() != null) {
            original.setActive(u.getActive());
        }

        return repo.save(original);
    }

    @PatchMapping("/{id}/toggle")
    public User toggleActive(@PathVariable Long id) {
        User u = repo.findById(id).orElseThrow();
        u.setActive(!Boolean.TRUE.equals(u.getActive()));
        return repo.save(u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
