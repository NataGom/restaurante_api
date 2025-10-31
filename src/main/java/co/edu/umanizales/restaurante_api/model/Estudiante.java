package co.edu.umanizales.restaurante_api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Estudiante extends Persona {
    private String matricula;
    private String carrera;
    private int semestre;
    
    // Agregación: un estudiante puede estar inscrito en varios cursos
    private List<Curso> cursos = new ArrayList<>();
    
    // Calificaciones del estudiante
    private List<Calificacion> calificaciones = new ArrayList<>();

    public Estudiante(Long id, String nombre, String email, String telefono, 
                     String matricula, String carrera, int semestre) {
        super(id, nombre, email, telefono);
        this.matricula = matricula;
        this.carrera = carrera;
        this.semestre = semestre;
        this.cursos = new ArrayList<>();
        this.calificaciones = new ArrayList<>();
    }

    @Override
    public String getRol() {
        return "ESTUDIANTE";
    }

    /**
     * Inscribe al estudiante en un curso
     */
    public void inscribirse(Curso curso) {
        if (cursos == null) {
            cursos = new ArrayList<>();
        }
        if (!cursos.contains(curso)) {
            cursos.add(curso);
            curso.agregarEstudiante(this);
        }
    }

    /**
     * Calcula y retorna el promedio de calificaciones del estudiante
     */
    public double consultarPromedio() {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0;
        }
        
        double suma = calificaciones.stream()
                .mapToDouble(Calificacion::getNota)
                .sum();
        
        return suma / calificaciones.size();
    }
    
    /**
     * Agrega una calificación al historial del estudiante
     */
    public void agregarCalificacion(Calificacion calificacion) {
        if (calificaciones == null) {
            calificaciones = new ArrayList<>();
        }
        calificaciones.add(calificacion);
    }
}
