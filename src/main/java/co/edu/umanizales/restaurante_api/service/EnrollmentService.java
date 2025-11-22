package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Enrollment;
import co.edu.umanizales.restaurante_api.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    
    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        // Resolve student and course for each enrollment
        for (Enrollment enrollment : enrollments) {
            resolveEnrollmentRelations(enrollment);
        }
        return enrollments;
    }

    public Enrollment findById(long id) {
        Enrollment enrollment = enrollmentRepository.findById(id);
        if (enrollment != null) {
            resolveEnrollmentRelations(enrollment);
        }
        return enrollment;
    }
    
    /**
     * Resolve student and course objects from their IDs
     */
    private void resolveEnrollmentRelations(Enrollment enrollment) {
        if (enrollment.getStudent() != null && enrollment.getStudent().getId() != 0) {
            Student student = studentService.findById(enrollment.getStudent().getId());
            if (student != null) {
                enrollment.setStudent(student);
            }
        }
        if (enrollment.getCourse() != null && enrollment.getCourse().getId() != 0) {
            Course course = courseService.findById(enrollment.getCourse().getId());
            if (course != null) {
                enrollment.setCourse(course);
            }
        }
    }

    // With record, relations are referenced by IDs; validation happens on creation/update

    public Enrollment save(Enrollment enrollment) {
        // Validate student exists
        if (enrollment.getStudent() == null || enrollment.getStudent().getId() == 0) {
            throw new RuntimeException("Student is required");
        }
        Student student = studentService.findById(enrollment.getStudent().getId());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        
        // Validate course exists
        if (enrollment.getCourse() == null || enrollment.getCourse().getId() == 0) {
            throw new RuntimeException("Course is required");
        }
        Course course = courseService.findById(enrollment.getCourse().getId());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        
        // Set complete objects
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        // Ensure enrollment date
        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(LocalDate.now());
        }
        
        // Set default status if not provided
        if (enrollment.getStatus() == null || enrollment.getStatus().isEmpty()) {
            enrollment.setStatus("ACTIVE");
        }
        
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment update(long id, Enrollment enrollment) {
        Enrollment existing = enrollmentRepository.findById(id);
        if (existing == null) {
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
        // Resolve student and course for each enrollment
        for (Enrollment enrollment : enrollments) {
            resolveEnrollmentRelations(enrollment);
        }
        return enrollments;
    }

    public List<Enrollment> findByCourseId(long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        // Resolve student and course for each enrollment
        for (Enrollment enrollment : enrollments) {
            resolveEnrollmentRelations(enrollment);
        }
        return enrollments;
    }

    public Enrollment enrollStudent(long studentId, long courseId) {
        // Validate and get student
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        
        // Validate and get course
        Course course = courseService.findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        
        // Create enrollment with complete objects
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ACTIVE");
        
        // Add student to course and course to student
        student.enrollInCourse(course);
        
        return enrollmentRepository.save(enrollment);
    }
}
