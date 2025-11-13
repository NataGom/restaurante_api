package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Teacher;
import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(long id) {
        return teacherRepository.findById(id);
    }

    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher update(long id, Teacher teacher) {
        Teacher existing = teacherRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Teacher not found with id: " + id);
        }
        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    public boolean deleteById(long id) {
        return teacherRepository.deleteById(id);
    }

    /**
     * Get all courses taught by a teacher
     */
    public List<Course> getTeacherCourses(long teacherId) {
        Teacher teacher = findById(teacherId);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        return teacher.getTaughtCourses() != null ? teacher.getTaughtCourses() : new ArrayList<>();
    }

    /**
     * Assign a course to a teacher
     */
    public void assignCourseToTeacher(long teacherId, Course course) {
        Teacher teacher = findById(teacherId);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        teacher.assignCourse(course);
        save(teacher);
    }
}
