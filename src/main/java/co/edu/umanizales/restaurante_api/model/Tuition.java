package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tuition {
    private long id;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String status; // ACTIVE, CANCELLED, COMPLETED
    private String period; // Example: 2024-1, 2024-2
}
