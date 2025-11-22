package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Tuition;
import co.edu.umanizales.restaurante_api.repository.TuitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TuitionService {
    
    private final TuitionRepository tuitionRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public List<Tuition> findAll() {
        List<Tuition> tuitions = tuitionRepository.findAll();
        // Resolve student and course for each tuition
        for (Tuition tuition : tuitions) {
            resolveTuitionRelations(tuition);
        }
        return tuitions;
    }

    public Tuition findById(long id) {
        Tuition tuition = tuitionRepository.findById(id);
        if (tuition != null) {
            resolveTuitionRelations(tuition);
        }
        return tuition;
    }
    
    /**
     * Resolve student and course objects from their IDs
     */
    private void resolveTuitionRelations(Tuition tuition) {
        if (tuition.getStudent() != null && tuition.getStudent().getId() != 0) {
            Student student = studentService.findById(tuition.getStudent().getId());
            if (student != null) {
                tuition.setStudent(student);
            }
        }
        if (tuition.getCourse() != null && tuition.getCourse().getId() != 0) {
            Course course = courseService.findById(tuition.getCourse().getId());
            if (course != null) {
                tuition.setCourse(course);
            }
        }
    }

    public Tuition save(Tuition tuition) {
        // Validate student exists
        if (tuition.getStudent() == null || tuition.getStudent().getId() == 0) {
            throw new RuntimeException("Student is required");
        }
        Student student = studentService.findById(tuition.getStudent().getId());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        
        // Validate course exists
        if (tuition.getCourse() == null || tuition.getCourse().getId() == 0) {
            throw new RuntimeException("Course is required");
        }
        Course course = courseService.findById(tuition.getCourse().getId());
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        
        // Set complete objects
        tuition.setStudent(student);
        tuition.setCourse(course);
        
        // Ensure enrollment date
        if (tuition.getEnrollmentDate() == null) {
            tuition.setEnrollmentDate(LocalDate.now());
        }
        
        // Set default status if not provided
        if (tuition.getStatus() == null || tuition.getStatus().isEmpty()) {
            tuition.setStatus("ACTIVE");
        }
        
        return tuitionRepository.save(tuition);
    }

    public Tuition update(long id, Tuition tuition) {
        Tuition existing = tuitionRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Tuition not found with id: " + id);
        }
        tuition.setId(id);
        return save(tuition);
    }

    public boolean deleteById(long id) {
        return tuitionRepository.deleteById(id);
    }

    public List<Tuition> findByStudentId(long studentId) {
        List<Tuition> tuitions = tuitionRepository.findByStudentId(studentId);
        // Resolve student and course for each tuition
        for (Tuition tuition : tuitions) {
            resolveTuitionRelations(tuition);
        }
        return tuitions;
    }

    public List<Tuition> findByCourseId(long courseId) {
        List<Tuition> tuitions = tuitionRepository.findByCourseId(courseId);
        // Resolve student and course for each tuition
        for (Tuition tuition : tuitions) {
            resolveTuitionRelations(tuition);
        }
        return tuitions;
    }

    public Tuition enrollStudent(long studentId, long courseId) {
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
        
        // Create tuition with complete objects
        Tuition tuition = new Tuition();
        tuition.setStudent(student);
        tuition.setCourse(course);
        tuition.setEnrollmentDate(LocalDate.now());
        tuition.setStatus("ACTIVE");
        
        // Add student to course and course to student
        student.enrollInCourse(course);
        
        return tuitionRepository.save(tuition);
    }
}
