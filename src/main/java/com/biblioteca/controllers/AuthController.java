package com.biblioteca.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.dto.LoginDTO;
import com.biblioteca.dto.LoginResponseDTO;
import com.biblioteca.dto.RegisterDTO;
import com.biblioteca.dto.RegisterResponseDTO;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.UserRepository;
import com.biblioteca.services.TokenService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO data) {
        var usersPassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usersPassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO("Logado com sucesso!", token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO data) {
        if(this.userRepository.findByUserCpf(data.cpf()) != null) return ResponseEntity.badRequest().build();
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserEntity newUser = new UserEntity(data.name(), data.email(), data.cpf(), data.type(), encryptedPassword);
        
        this.userRepository.save(newUser);
        return ResponseEntity.ok(new RegisterResponseDTO("Usuário criado com sucesso!"));
    }
}
