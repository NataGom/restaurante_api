package co.edu.umanizales.restaurante_api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Teacher extends Person {
    private String specialty;
    private int yearsOfExperience;
    
    // Composition: a teacher can teach multiple courses
    private List<Course> taughtCourses = new ArrayList<>();

    public Teacher(long id, String name, String email, String phone, 
                   String specialty, int yearsOfExperience) {
        super(id, name, email, phone);
        this.specialty = specialty;
        this.yearsOfExperience = yearsOfExperience;
        this.taughtCourses = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "TEACHER";
    }

    /**
     * Assigns a course to the teacher
     */
    public void assignCourse(Course course) {
        if (taughtCourses == null) {
            taughtCourses = new ArrayList<>();
        }
        if (!taughtCourses.contains(course)) {
            taughtCourses.add(course);
            course.setTeacher(this);
        }
    }

    /**
     * Grades a student in a subject
     */
    public Grade gradeStudent(Student student, Subject subject, double score) {
        // Validate that the score is in a valid range (0-100)
        if (score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException("Score must be between 0.0 and 100.0");
        }
        
        // Create the grade
        Grade grade = new Grade();
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setScore(score);
        grade.setDate(LocalDate.now());
        grade.setGradedBy(this);
        
        // Add the grade to the student's history
        student.addGrade(grade);
        
        return grade;
    }
}
