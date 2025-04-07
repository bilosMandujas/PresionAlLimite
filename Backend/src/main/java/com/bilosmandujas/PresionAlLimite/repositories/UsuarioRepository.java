package com.bilosmandujas.PresionAlLimite.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bilosmandujas.PresionAlLimite.Modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);
    
}