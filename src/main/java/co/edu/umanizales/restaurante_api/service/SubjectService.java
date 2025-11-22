package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Subject;
import co.edu.umanizales.restaurante_api.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects;
    }

    public Subject findById(long id) {
        return subjectRepository.findById(id);
    }

    public Subject save(Subject subject) {
        Subject saved = subjectRepository.save(subject);
        // Ensure the saved subject is returned with all data
        return findById(saved.getId());
    }

    public Subject update(long id, Subject subject) {
        Subject existing = subjectRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Subject not found with id: " + id);
        }
        subject.setId(id);
        return subjectRepository.save(subject);
    }

    public boolean deleteById(long id) {
        return subjectRepository.deleteById(id);
    }
}
