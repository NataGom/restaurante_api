package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Enrollment;
import co.edu.umanizales.restaurante_api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        // Load student and course for each enrollment
        enrollments.forEach(this::loadRelations);
        return enrollments;
    }

    public Optional<Enrollment> findById(long id) {
        Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(id);
        enrollmentOpt.ifPresent(this::loadRelations);
        return enrollmentOpt;
    }

    private void loadRelations(Enrollment enrollment) {
        if (enrollment.getStudent() != null && enrollment.getStudent().getId() != 0) {
            studentService.findById(enrollment.getStudent().getId())
                .ifPresent(enrollment::setStudent);
        }
        if (enrollment.getCourse() != null && enrollment.getCourse().getId() != 0) {
            courseService.findById(enrollment.getCourse().getId())
                .ifPresent(enrollment::setCourse);
        }
    }

    public Enrollment save(Enrollment enrollment) {
        // Validate student exists
        if (enrollment.getStudent() != null && enrollment.getStudent().getId() != 0) {
            Student student = studentService.findById(enrollment.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
            enrollment.setStudent(student);
        }
        
        // Validate course exists
        if (enrollment.getCourse() != null && enrollment.getCourse().getId() != 0) {
            Course course = courseService.findById(enrollment.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
            enrollment.setCourse(course);
        }
        
        // Set default values
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }
        if (enrollment.getStatus() == null || enrollment.getStatus().isEmpty()) {
            enrollment.setStatus("ACTIVE");
        }
        
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment update(long id, Enrollment enrollment) {
        if (!enrollmentRepository.findById(id).isPresent()) {
            throw new RuntimeException("Enrollment not found with id: " + id);
        }
        enrollment.setId(id);
        return save(enrollment);
    }

    public boolean deleteById(long id) {
        return enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> findByStudentId(long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        enrollments.forEach(this::loadRelations);
        return enrollments;
    }

    public List<Enrollment> findByCourseId(long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        enrollments.forEach(this::loadRelations);
        return enrollments;
    }

    public Enrollment enrollStudent(long studentId, long courseId, String period) {
        Student student = studentService.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseService.findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPeriod(period);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");

        return enrollmentRepository.save(enrollment);
    }
}
