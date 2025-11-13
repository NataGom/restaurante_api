package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Grade;
import co.edu.umanizales.restaurante_api.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(long id) {
        return studentRepository.findById(id);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student update(long id, Student student) {
        Student existing = studentRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public boolean deleteById(long id) {
        return studentRepository.deleteById(id);
    }

    /**
     * Get all courses for a student
     */
    public List<Course> getStudentCourses(long studentId) {
        Student student = findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        return student.getCourses() != null ? student.getCourses() : new ArrayList<>();
    }

    /**
     * Get all grades for a student
     */
    public List<Grade> getStudentGrades(long studentId) {
        Student student = findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        return student.getGrades() != null ? student.getGrades() : new ArrayList<>();
    }

    /**
     * Calculate average grade for a student
     */
    public double getStudentAverage(long studentId) {
        Student student = findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        return student.calculateAverage();
    }

    /**
     * Add a grade to a student
     */
    public void addGradeToStudent(long studentId, Grade grade) {
        Student student = findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        student.addGrade(grade);
        save(student);
    }
}
