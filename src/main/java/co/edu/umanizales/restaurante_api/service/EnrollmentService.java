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
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> findById(long id) {
        return enrollmentRepository.findById(id);
    }

    // With record, relations are referenced by IDs; validation happens on creation/update

    public Enrollment save(Enrollment enrollment) {
        // Validate referenced IDs
        if (enrollment.getStudent() == null || studentService.findById(enrollment.getStudent().getId()).isEmpty()) {
            throw new RuntimeException("Student not found");
        }
        if (enrollment.courseId() == 0 || courseService.findById(enrollment.courseId()).isEmpty()) {
            throw new RuntimeException("Course not found");
        }
        // Ensure date
        Enrollment toPersist = enrollment.date() == null
                ? new Enrollment(enrollment.id(), enrollment.studentId(), enrollment.courseId(), LocalDate.now())
                : enrollment;
        return enrollmentRepository.save(toPersist);
    }

    public Enrollment update(long id, Enrollment enrollment) {
        if (enrollmentRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Enrollment not found with id: " + id);
        }
        Enrollment withId = new Enrollment(id, enrollment.studentId(), enrollment.courseId(), 
                enrollment.date() == null ? LocalDate.now() : enrollment.date());
        return save(withId);
    }

    public boolean deleteById(long id) {
        return enrollmentRepository.deleteById(id);
    }

    public List<Enrollment> findByStudentId(long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    public List<Enrollment> findByCourseId(long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public Enrollment enrollStudent(long studentId, long courseId) {
        // Validate existence
        studentService.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        courseService.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Enrollment enrollment = new Enrollment(0, studentId, courseId, LocalDate.now());
        return enrollmentRepository.save(enrollment);
    }
}
