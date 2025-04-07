package com.bilosmandujas.PresionAlLimite.services;


import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bilosmandujas.PresionAlLimite.Modelo.Usuario;
import com.bilosmandujas.PresionAlLimite.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor público para inyección de dependencias
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public ResponseEntity<Usuario> registrarUsuario(Usuario usuario) {
        // Codificar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);  // Devolvemos ResponseEntity con el objeto Usuario
    }

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);  // Asegúrate de que 'findByCorreo' esté bien definido en el repositorio
    }

    // Método para validar las credenciales
    public boolean validarCredenciales(String correo, String passwordPlano) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);
        return usuarioOpt.isPresent() && passwordEncoder.matches(passwordPlano, usuarioOpt.get().getPassword());
    }

}