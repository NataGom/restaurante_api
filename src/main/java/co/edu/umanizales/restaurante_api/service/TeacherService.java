package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Teacher;
import co.edu.umanizales.restaurante_api.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findById(long id) {
        return teacherRepository.findById(id);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(long id, Teacher teacher) {
        if (!teacherRepository.findById(id).isPresent()) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    public boolean deleteById(long id) {
        return teacherRepository.deleteById(id);
    }
}
