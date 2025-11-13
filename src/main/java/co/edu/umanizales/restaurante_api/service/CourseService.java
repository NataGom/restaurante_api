package co.edu.umanizales.restaurante_api.service;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Teacher;
import co.edu.umanizales.restaurante_api.model.Subject;
import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Schedule;
import co.edu.umanizales.restaurante_api.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public List<Course> findAll() {
        List<Course> courses = courseRepository.findAll();
        // Load teacher for each course
        for (Course course : courses) {
            if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
                Teacher teacher = teacherService.findById(course.getTeacher().getId());
                if (teacher != null) {
                    course.setTeacher(teacher);
                }
            }
        }
        return courses;
    }

    public Course findById(long id) {
        Course course = courseRepository.findById(id);
        if (course != null) {
            if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
                Teacher teacher = teacherService.findById(course.getTeacher().getId());
                if (teacher != null) {
                    course.setTeacher(teacher);
                }
            }
        }
        return course;
    }

    public Course save(Course course) {
        if (course.getTeacher() != null && course.getTeacher().getId() != 0) {
            Teacher teacher = teacherService.findById(course.getTeacher().getId());
            if (teacher == null) {
                throw new RuntimeException("Teacher not found");
            }
            course.setTeacher(teacher);
        }
        return courseRepository.save(course);
    }

    public Course update(long id, Course course) {
        Course existing = courseRepository.findById(id);
        if (existing == null) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        course.setId(id);
        return save(course);
    }

    public boolean deleteById(long id) {
        return courseRepository.deleteById(id);
    }

    public Course assignTeacher(long courseId, long teacherId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        Teacher teacher = teacherService.findById(teacherId);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        
        course.setTeacher(teacher);
        return courseRepository.save(course);
    }

    /**
     * Add a subject to a course
     */
    public Course addSubjectToCourse(long courseId, long subjectId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        Subject subject = subjectService.findById(subjectId);
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }
        
        course.addSubject(subject);
        return courseRepository.save(course);
    }

    /**
     * Add a student to a course
     */
    public Course addStudentToCourse(long courseId, long studentId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }
        
        course.addStudent(student);
        student.enrollInCourse(course);
        return courseRepository.save(course);
    }

    /**
     * Add a schedule to a course
     */
    public Course addScheduleToCourse(long courseId, Schedule schedule) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        
        course.addSchedule(schedule);
        return courseRepository.save(course);
    }

    /**
     * Get all subjects in a course
     */
    public List<Subject> getSubjectsInCourse(long courseId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return course.getSubjects() != null ? course.getSubjects() : new java.util.ArrayList<>();
    }

    /**
     * Get all students in a course
     */
    public List<Student> getStudentsInCourse(long courseId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return course.listStudents();
    }

    /**
     * Get all schedules for a course
     */
    public List<Schedule> getSchedulesForCourse(long courseId) {
        Course course = findById(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found");
        }
        return course.listSchedules();
    }
}
