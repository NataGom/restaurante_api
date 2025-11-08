package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Subject;
import co.edu.umanizales.restaurante_api.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> findById(long id) {
        return subjectRepository.findById(id);
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject update(long id, Subject subject) {
        if (!subjectRepository.findById(id).isPresent()) {
            throw new RuntimeException("Subject not found with id: " + id);
        }
        subject.setId(id);
        return subjectRepository.save(subject);
    }

    public boolean deleteById(long id) {
        return subjectRepository.deleteById(id);
    }
}
