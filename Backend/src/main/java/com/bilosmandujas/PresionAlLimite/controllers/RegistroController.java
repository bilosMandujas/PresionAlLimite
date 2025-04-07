package com.bilosmandujas.PresionAlLimite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bilosmandujas.PresionAlLimite.services.RegistroService;
import com.bilosmandujas.PresionAlLimite.Modelo.Registro;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    // Crear o actualizar un registro
    @PostMapping
    public ResponseEntity<Registro> crearRegistro(@RequestBody Registro registro) {
        System.out.println("Datos recibidos: " + registro); 
        // Se asume que este método solo se usará para crear registros nuevos
        Registro resultado = registroService.guardarRegistro(registro);
        return new ResponseEntity<>(resultado, HttpStatus.CREATED); // Retorna con el estado 201 (creado)
    }

    // Obtener un registro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Registro> obtenerRegistro(@PathVariable Long id) {
        Registro registro = registroService.obtenerPorId(id);
        if (registro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el registro, 404
        }
        return new ResponseEntity<>(registro, HttpStatus.OK); // Si se encuentra, 200
    }

    // Actualizar un registro
    @PutMapping("/{id}")
    public ResponseEntity<Registro> actualizarRegistro(@PathVariable Long id, @RequestBody Registro registro) {
        // Primero verificamos si el registro existe
        Registro registroExistente = registroService.obtenerPorId(id);
        
        if (registroExistente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el registro, 404
        }
        
        // Si existe, se actualiza el registro
        registro.setId(id); // Asegúrate de que el ID del registro coincide con el ID en la URL
        Registro resultado = registroService.guardarRegistro(registro); // Guardar o actualizar el registro
        return new ResponseEntity<>(resultado, HttpStatus.OK); // Retorna el resultado con estado 200
    }

    // Eliminar un registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long id) {
        Registro registro = registroService.obtenerPorId(id);
        if (registro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el registro, 404
        }
        
        registroService.eliminarRegistro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Respuesta 204 (sin contenido) si la eliminación es exitosa
    }
}
