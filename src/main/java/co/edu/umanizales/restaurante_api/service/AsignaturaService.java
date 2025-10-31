package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Asignatura;
import co.edu.umanizales.restaurante_api.repository.AsignaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AsignaturaService {
    
    private final AsignaturaRepository asignaturaRepository;

    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    public Optional<Asignatura> findById(Long id) {
        return asignaturaRepository.findById(id);
    }

    public Asignatura save(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public Asignatura update(Long id, Asignatura asignatura) {
        if (!asignaturaRepository.findById(id).isPresent()) {
            throw new RuntimeException("Asignatura no encontrada con id: " + id);
        }
        asignatura.setId(id);
        return asignaturaRepository.save(asignatura);
    }

    public boolean deleteById(Long id) {
        return asignaturaRepository.deleteById(id);
    }
}
