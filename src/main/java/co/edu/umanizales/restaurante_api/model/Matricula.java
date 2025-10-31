package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {
    private Long id;
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechaMatricula;
    private String estado; // ACTIVA, CANCELADA, FINALIZADA
    private String periodo; // Ejemplo: 2024-1, 2024-2
}
