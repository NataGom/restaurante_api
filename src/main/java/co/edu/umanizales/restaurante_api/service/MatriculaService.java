package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Curso;
import co.edu.umanizales.restaurante_api.model.Estudiante;
import co.edu.umanizales.restaurante_api.model.Matricula;
import co.edu.umanizales.restaurante_api.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatriculaService {
    
    private final MatriculaRepository matriculaRepository;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public List<Matricula> findAll() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        // Load estudiante and curso for each matricula
        matriculas.forEach(this::loadRelations);
        return matriculas;
    }

    public Optional<Matricula> findById(Long id) {
        Optional<Matricula> matriculaOpt = matriculaRepository.findById(id);
        matriculaOpt.ifPresent(this::loadRelations);
        return matriculaOpt;
    }

    private void loadRelations(Matricula matricula) {
        if (matricula.getEstudiante() != null && matricula.getEstudiante().getId() != null) {
            estudianteService.findById(matricula.getEstudiante().getId())
                .ifPresent(matricula::setEstudiante);
        }
        if (matricula.getCurso() != null && matricula.getCurso().getId() != null) {
            cursoService.findById(matricula.getCurso().getId())
                .ifPresent(matricula::setCurso);
        }
    }

    public Matricula save(Matricula matricula) {
        // Validate estudiante exists
        if (matricula.getEstudiante() != null && matricula.getEstudiante().getId() != null) {
            Estudiante estudiante = estudianteService.findById(matricula.getEstudiante().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            matricula.setEstudiante(estudiante);
        }
        
        // Validate curso exists
        if (matricula.getCurso() != null && matricula.getCurso().getId() != null) {
            Curso curso = cursoService.findById(matricula.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            matricula.setCurso(curso);
        }
        
        // Set default values
        if (matricula.getFechaMatricula() == null) {
            matricula.setFechaMatricula(LocalDate.now());
        }
        if (matricula.getEstado() == null || matricula.getEstado().isEmpty()) {
            matricula.setEstado("ACTIVA");
        }
        
        return matriculaRepository.save(matricula);
    }

    public Matricula update(Long id, Matricula matricula) {
        if (!matriculaRepository.findById(id).isPresent()) {
            throw new RuntimeException("Matricula no encontrada con id: " + id);
        }
        matricula.setId(id);
        return save(matricula);
    }

    public boolean deleteById(Long id) {
        return matriculaRepository.deleteById(id);
    }

    public List<Matricula> findByEstudianteId(Long estudianteId) {
        List<Matricula> matriculas = matriculaRepository.findByEstudianteId(estudianteId);
        matriculas.forEach(this::loadRelations);
        return matriculas;
    }

    public List<Matricula> findByCursoId(Long cursoId) {
        List<Matricula> matriculas = matriculaRepository.findByCursoId(cursoId);
        matriculas.forEach(this::loadRelations);
        return matriculas;
    }

    public Matricula matricularEstudiante(Long estudianteId, Long cursoId, String periodo) {
        Estudiante estudiante = estudianteService.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Curso curso = cursoService.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setCurso(curso);
        matricula.setPeriodo(periodo);
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("ACTIVA");

        return matriculaRepository.save(matricula);
    }
}
