package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Curso;
import co.edu.umanizales.restaurante_api.model.Profesor;
import co.edu.umanizales.restaurante_api.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoService {
    
    private final CursoRepository cursoRepository;
    private final ProfesorService profesorService;

    public List<Curso> findAll() {
        List<Curso> cursos = cursoRepository.findAll();
        // Load profesor for each curso
        cursos.forEach(curso -> {
            if (curso.getProfesor() != null && curso.getProfesor().getId() != null) {
                profesorService.findById(curso.getProfesor().getId())
                    .ifPresent(curso::setProfesor);
            }
        });
        return cursos;
    }

    public Optional<Curso> findById(Long id) {
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        cursoOpt.ifPresent(curso -> {
            if (curso.getProfesor() != null && curso.getProfesor().getId() != null) {
                profesorService.findById(curso.getProfesor().getId())
                    .ifPresent(curso::setProfesor);
            }
        });
        return cursoOpt;
    }

    public Curso save(Curso curso) {
        if (curso.getProfesor() != null && curso.getProfesor().getId() != null) {
            Profesor profesor = profesorService.findById(curso.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
            curso.setProfesor(profesor);
        }
        return cursoRepository.save(curso);
    }

    public Curso update(Long id, Curso curso) {
        if (!cursoRepository.findById(id).isPresent()) {
            throw new RuntimeException("Curso no encontrado con id: " + id);
        }
        curso.setId(id);
        return save(curso);
    }

    public boolean deleteById(Long id) {
        return cursoRepository.deleteById(id);
    }

    public Curso asignarProfesor(Long cursoId, Long profesorId) {
        Curso curso = findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        Profesor profesor = profesorService.findById(profesorId)
            .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        
        curso.setProfesor(profesor);
        return cursoRepository.save(curso);
    }
}
