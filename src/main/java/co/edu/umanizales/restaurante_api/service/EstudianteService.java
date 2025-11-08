package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(long id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student update(long id, Student student) {
        if (!studentRepository.findById(id).isPresent()) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public boolean deleteById(long id) {
        return studentRepository.deleteById(id);
    }
}
