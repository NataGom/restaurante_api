package co.edu.umanizales.restaurante_api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Student extends Person {
    private String studentId;
    private String major;
    private int semester;
    
    // Aggregation: a student can be enrolled in multiple courses
    private List<Course> courses = new ArrayList<>();
    
    // Student's grades
    private List<Grade> grades = new ArrayList<>();

    public Student(long id, String name, String email, String phone, 
                   String studentId, String major, int semester) {
        super(id, name, email, phone);
        this.studentId = studentId;
        this.major = major;
        this.semester = semester;
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    /**
     * Enrolls the student in a course
     */
    public void enrollInCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        if (!courses.contains(course)) {
            courses.add(course);
            course.addStudent(this);
        }
    }

    /**
     * Calculates and returns the student's grade average
     */
    public double calculateAverage() {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        
        double sum = grades.stream()
                .mapToDouble(Grade::getScore)
                .sum();
        
        return sum / grades.size();
    }
    
    /**
     * Adds a grade to the student's history
     */
    public void addGrade(Grade grade) {
        if (grades == null) {
            grades = new ArrayList<>();
        }
        grades.add(grade);
    }
}
