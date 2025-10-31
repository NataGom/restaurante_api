package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Profesor;
import co.edu.umanizales.restaurante_api.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfesorService {
    
    private final ProfesorRepository profesorRepository;

    public List<Profesor> findAll() {
        return profesorRepository.findAll();
    }

    public Optional<Profesor> findById(Long id) {
        return profesorRepository.findById(id);
    }

    public Profesor save(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public Profesor update(Long id, Profesor profesor) {
        if (!profesorRepository.findById(id).isPresent()) {
            throw new RuntimeException("Profesor no encontrado con id: " + id);
        }
        profesor.setId(id);
        return profesorRepository.save(profesor);
    }

    public boolean deleteById(Long id) {
        return profesorRepository.deleteById(id);
    }
}
