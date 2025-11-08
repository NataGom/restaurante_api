package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private long id;
    private String code;
    private String name;
    private int credits;
    private Mode mode;
    
    // Composition: a course always requires a teacher
    private Teacher teacher;
    
    // Aggregation: a course can have multiple subjects
    private List<Subject> subjects = new ArrayList<>();
    
    // Aggregation: a course can have multiple students
    private List<Student> students = new ArrayList<>();

    /**
     * Adds a subject to the course
     */
    public void addSubject(Subject subject) {
        if (subjects == null) {
            subjects = new ArrayList<>();
        }
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    /**
     * Lists all students enrolled in the course
     */
    public List<Student> listStudents() {
        return students != null ? new ArrayList<>(students) : new ArrayList<>();
    }
    
    /**
     * Adds a student to the course
     */
    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        if (!students.contains(student)) {
            students.add(student);
        }
    }
}
