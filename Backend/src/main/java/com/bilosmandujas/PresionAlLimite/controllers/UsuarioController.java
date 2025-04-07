package com.bilosmandujas.PresionAlLimite.controllers;

import com.bilosmandujas.PresionAlLimite.Modelo.Usuario;
import com.bilosmandujas.PresionAlLimite.services.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*")


public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Registrar un nuevo usuario", responses = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en el registro")
    })
    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        ResponseEntity<Usuario> respuesta = usuarioService.registrarUsuario(usuario);
        if (respuesta.getStatusCodeValue() == 409) {
            return ResponseEntity.status(409).body("Correo ya registrado");
        }
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @Operation(summary = "Iniciar sesión de usuario", responses = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
public ResponseEntity<LoginResponse> iniciarSesion(@RequestBody Usuario usuario) {
    try {
        boolean esValido = usuarioService.validarCredenciales(usuario.getCorreo(), usuario.getPassword());

        if (esValido) {
            String token = generarToken(usuario);
            LoginResponse response = new LoginResponse("Inicio de sesión exitoso", token);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(new LoginResponse("Credenciales incorrectas", null));
    } catch (Exception e) {
        // Logear el error para obtener más detalles
        e.printStackTrace();
        return ResponseEntity.status(500).body(new LoginResponse("Error interno del servidor", null));
    }
}


        // Método para generar el JWT
    public String generarToken(Usuario usuario) {
    long expirationTime = 1000 * 60 * 60; // 1 hora
    String secretKey = "mi_clave_super_secreta_y_segura_123456789"; // Cambia por una clave más robusta
    Key hmacKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    return Jwts.builder()
            .setSubject(usuario.getCorreo())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(hmacKey)
            .compact();

    }
}