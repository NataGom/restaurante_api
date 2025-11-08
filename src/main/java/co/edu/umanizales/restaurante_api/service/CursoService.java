package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Teacher;
import co.edu.umanizales.restaurante_api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        // Load teacher for each course
        courses.forEach(course -> {
            if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
                teacherService.findById(course.getTeacher().getId())
                    .ifPresent(course::setTeacher);
            }
        });
        return courses;
    }

    public Optional<Course> findById(long id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        courseOpt.ifPresent(course -> {
            if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
                teacherService.findById(course.getTeacher().getId())
                    .ifPresent(course::setTeacher);
            }
        });
        return courseOpt;
    }

    public Course save(Course course) {
        if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
            Teacher teacher = teacherService.findById(course.getTeacher().getId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
            course.setTeacher(teacher);
        }
        return courseRepository.save(course);
    }

    public Course update(long id, Course course) {
        if (!courseRepository.findById(id).isPresent()) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        course.setId(id);
        return save(course);
    }

    public boolean deleteById(long id) {
        return courseRepository.deleteById(id);
    }

    public Course assignTeacher(long courseId, long teacherId) {
        Course course = findById(courseId)
            .orElseThrow(() -> new RuntimeException("Course not found"));
        Teacher teacher = teacherService.findById(teacherId)
            .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }
}
