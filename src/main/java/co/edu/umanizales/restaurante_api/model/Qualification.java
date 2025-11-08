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
}
