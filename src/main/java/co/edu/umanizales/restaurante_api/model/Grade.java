package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private long id;
    private Student student;
    private Subject subject;
    private Course course;
    private double score;
    private LocalDate date;
    private Teacher gradedBy;
    
    // Minimum passing score (0-100 scale)
    private static final double PASSING_SCORE = 60.0;
    
    /**
     * Checks if the grade is passing
     * @return true if score >= 60.0, false otherwise
     */
    public boolean isPassed() {
        return score >= PASSING_SCORE;
    }
    
    /**
     * Gets detailed information about the grade
     * @return formatted string with grade details
     */
    public String getDetails() {
        return String.format("Grade Details:\n" +
                "  Student: %s\n" +
                "  Subject: %s\n" +
                "  Score: %.2f\n" +
                "  Status: %s\n" +
                "  Date: %s\n" +
                "  Graded by: %s",
                student != null ? student.getName() : "N/A",
                subject != null ? subject.getName() : "N/A",
                score,
                isPassed() ? "PASSED" : "FAILED",
                date != null ? date.toString() : "N/A",
                gradedBy != null ? gradedBy.getName() : "N/A"
        );
    }
}
