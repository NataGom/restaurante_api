package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calificacion {
    private Long id;
    private Estudiante estudiante;
    private Asignatura asignatura;
    private Curso curso;
    private double nota;
    private LocalDate fecha;
    private Profesor profesorCalificador;
}
