package com.bilosmandujas.PresionAlLimite.Modelo;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@Entity
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto genera el id automáticamente
    private Long id;

    private double presionAlta;

    private double presionBaja;

    private int ritmoCardiaco;

    private String observaciones;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;

    // Agregar campo de versión para bloqueo optimista
    @Version
    private Long version;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPresionAlta() {
        return presionAlta;
    }

    public void setPresionAlta(double presionAlta) {
        this.presionAlta = presionAlta;
    }

    public double getPresionBaja() {
        return presionBaja;
    }

    public void setPresionBaja(double presionBaja) {
        this.presionBaja = presionBaja;
    }

    public int getRitmoCardiaco() {
        return ritmoCardiaco;
    }

    public void setRitmoCardiaco(int ritmoCardiaco) {
        this.ritmoCardiaco = ritmoCardiaco;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    // Getters y Setters para el campo de versión
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
