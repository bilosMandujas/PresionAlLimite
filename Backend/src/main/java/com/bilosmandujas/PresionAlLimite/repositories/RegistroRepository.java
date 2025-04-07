package com.bilosmandujas.PresionAlLimite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bilosmandujas.PresionAlLimite.Modelo.Registro;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    
}
