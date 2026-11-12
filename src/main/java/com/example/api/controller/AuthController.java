package com.example.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.api.dto.AuthResponse;
import com.example.api.dto.LoginRequest;
import com.example.api.dto.RegistroRequest;
import com.example.api.entity.Usuario;
import com.example.api.exception.ReglaNegocioException;
import com.example.api.repository.UsuarioRepository;
import com.example.api.security.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, UsuarioRepository usuarioRepo,
                          PasswordEncoder encoder, JwtService jwtService) {
        this.authManager = authManager;
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        UserDetails user = (UserDetails) auth.getPrincipal();
        Usuario u = usuarioRepo.findByUsername(user.getUsername()).orElseThrow();
        String token = jwtService.generarToken(user, u.getNombre());
        return new AuthResponse(token, "Bearer", u.getUsername(), u.getNombre());
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@Valid @RequestBody RegistroRequest req) {
        if (usuarioRepo.existsByUsername(req.username())) {
            throw new ReglaNegocioException("El usuario " + req.username() + " ya existe");
        }
        Usuario u = new Usuario();
        u.setUsername(req.username());
        u.setPassword(encoder.encode(req.password()));
        u.setNombre(req.nombre());
        u.setRol("ROLE_ADMIN");
        usuarioRepo.save(u);

        UserDetails user = User.withUsername(u.getUsername())
                .password(u.getPassword()).authorities(u.getRol()).build();
        String token = jwtService.generarToken(user, u.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, "Bearer", u.getUsername(), u.getNombre()));
    }
}