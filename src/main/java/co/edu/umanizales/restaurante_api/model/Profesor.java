package co.edu.umanizales.restaurante_api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Profesor extends Persona {
    private String especialidad;
    private int antiguedad; // años de antigüedad
    
    // Composición: un profesor puede impartir varios cursos
    private List<Curso> cursosImpartidos = new ArrayList<>();

    public Profesor(Long id, String nombre, String email, String telefono, 
                   String especialidad, int antiguedad) {
        super(id, nombre, email, telefono);
        this.especialidad = especialidad;
        this.antiguedad = antiguedad;
        this.cursosImpartidos = new ArrayList<>();
    }

    @Override
    public String getRol() {
        return "PROFESOR";
    }

    /**
     * Asigna un curso al profesor
     */
    public void asignarCurso(Curso curso) {
        if (cursosImpartidos == null) {
            cursosImpartidos = new ArrayList<>();
        }
        if (!cursosImpartidos.contains(curso)) {
            cursosImpartidos.add(curso);
            curso.setProfesor(this);
        }
    }

    /**
     * Califica a un estudiante en una asignatura
     */
    public Calificacion calificar(Estudiante estudiante, Asignatura asignatura, double nota) {
        // Validar que la nota esté en un rango válido (0-5 típicamente en Colombia)
        if (nota < 0.0 || nota > 5.0) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0");
        }
        
        // Crear la calificación
        Calificacion calificacion = new Calificacion();
        calificacion.setEstudiante(estudiante);
        calificacion.setAsignatura(asignatura);
        calificacion.setNota(nota);
        calificacion.setFecha(LocalDate.now());
        calificacion.setProfesorCalificador(this);
        
        // Agregar la calificación al historial del estudiante
        estudiante.agregarCalificacion(calificacion);
        
        return calificacion;
    }
}
