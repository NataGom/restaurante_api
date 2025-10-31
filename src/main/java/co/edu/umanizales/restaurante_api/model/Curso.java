package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    private Long id;
    private String codigo;
    private String nombre;
    private int creditos;
    private Modalidad modalidad;
    
    // Composición: un curso siempre requiere un profesor
    private Profesor profesor;
    
    // Agregación: un curso puede tener varias asignaturas
    private List<Asignatura> asignaturas = new ArrayList<>();
    
    // Agregación: un curso puede tener varios estudiantes
    private List<Estudiante> estudiantes = new ArrayList<>();

    /**
     * Agrega una asignatura al curso
     */
    public void agregarAsignatura(Asignatura asignatura) {
        if (asignaturas == null) {
            asignaturas = new ArrayList<>();
        }
        if (!asignaturas.contains(asignatura)) {
            asignaturas.add(asignatura);
        }
    }

    /**
     * Lista todos los estudiantes inscritos en el curso
     */
    public List<Estudiante> listarEstudiantes() {
        return estudiantes != null ? new ArrayList<>(estudiantes) : new ArrayList<>();
    }
    
    /**
     * Agrega un estudiante al curso
     */
    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiantes == null) {
            estudiantes = new ArrayList<>();
        }
        if (!estudiantes.contains(estudiante)) {
            estudiantes.add(estudiante);
        }
    }
}
