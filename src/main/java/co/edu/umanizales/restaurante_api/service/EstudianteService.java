package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Estudiante;
import co.edu.umanizales.restaurante_api.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteService {
    
    private final EstudianteRepository estudianteRepository;

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public Estudiante update(Long id, Estudiante estudiante) {
        if (!estudianteRepository.findById(id).isPresent()) {
            throw new RuntimeException("Estudiante no encontrado con id: " + id);
        }
        estudiante.setId(id);
        return estudianteRepository.save(estudiante);
    }

    public boolean deleteById(Long id) {
        return estudianteRepository.deleteById(id);
    }
}
