package com.bilosmandujas.PresionAlLimite.Modelo;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;  // Campo de control de concurrencia

    private String nombre;
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String correo;

    private String password;

    private Integer edad;

    @Column(name = "fecha_nacimiento") // Corregido el nombre de la columna
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USER;

    private Boolean estado = true;

    public enum Sexo {
        M, F, Otro
    }

    public enum Rol {
        USER, ADMIN
    }
}
