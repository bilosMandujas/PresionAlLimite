package com.bilosmandujas.PresionAlLimite.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import com.bilosmandujas.PresionAlLimite.Modelo.Registro;
import com.bilosmandujas.PresionAlLimite.repositories.RegistroRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;
    
    @Transactional
    public Registro guardarRegistro(Registro registro) {
        try {
            return registroRepository.save(registro);
        } catch (OptimisticLockingFailureException e) {
            // Manejar la excepción, por ejemplo, reintentando la operación
            throw new RuntimeException("Conflicto de concurrencia detectado", e);
        }
    }
    
    @Transactional
    public Registro obtenerPorId(Long id) {
        Optional<Registro> registro = registroRepository.findById(id);
        if (registro.isPresent()) {
            return registro.get();
        } else {
            throw new RuntimeException("Registro no encontrado con ID: " + id);
        }
    }
    @Transactional
    public void eliminarRegistro(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Registro no encontrado con ID: " + id);
        }
        registroRepository.deleteById(id);
    }
}
