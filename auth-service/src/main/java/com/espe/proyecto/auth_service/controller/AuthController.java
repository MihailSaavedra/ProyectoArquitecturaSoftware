package com.espe.proyecto.auth_service.controller;
import com.espe.proyecto.auth_service.config.JwtUtil;
import com.espe.proyecto.auth_service.model.Usuario;
import com.espe.proyecto.auth_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está en uso.");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .rol(req.getRol())
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(req.getEmail());

        if (usuarioOptional.isEmpty() || !passwordEncoder.matches(req.getPassword(), usuarioOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }

        Usuario usuario = usuarioOptional.get();
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());

        return ResponseEntity.ok(new TokenResponse(token));
    }
}