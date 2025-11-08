package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Enrollment;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepository extends CsvRepository<Enrollment> {
    
    private static final String FILE_PATH = "data/enrollments.csv";

    public EnrollmentRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,studentId,courseId,enrollmentDate,status,period";
    }

    @Override
    protected String toCsv(Enrollment enrollment) {
        long studentId = enrollment.getStudent() != null ? enrollment.getStudent().getId() : 0;
        long courseId = enrollment.getCourse() != null ? enrollment.getCourse().getId() : 0;
        
        return String.join(",",
            String.valueOf(enrollment.getId()),
            studentId != 0 ? String.valueOf(studentId) : "",
            courseId != 0 ? String.valueOf(courseId) : "",
            enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().toString() : "",
            escapeCsv(enrollment.getStatus()),
            escapeCsv(enrollment.getPeriod())
        );
    }

    @Override
    protected Enrollment fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Enrollment enrollment = new Enrollment();
        enrollment.setId(Long.parseLong(fields[0]));
        
        // IDs will be resolved in service layer
        
        if (!fields[3].isEmpty()) {
            enrollment.setEnrollmentDate(LocalDate.parse(fields[3]));
        }
        enrollment.setStatus(unescapeCsv(fields[4]));
        enrollment.setPeriod(unescapeCsv(fields[5]));
        
        return enrollment;
    }

    @Override
    protected long getId(Enrollment enrollment) {
        return enrollment.getId();
    }

    @Override
    protected void setId(Enrollment enrollment, long id) {
        enrollment.setId(id);
    }

    /**
     * Find enrollments by student ID
     */
    public List<Enrollment> findByStudentId(long studentId) {
        return findAll().stream()
            .filter(m -> m.getStudent() != null && m.getStudent().getId() == studentId)
            .collect(Collectors.toList());
    }

    /**
     * Find enrollments by course ID
     */
    public List<Enrollment> findByCourseId(long courseId) {
        return findAll().stream()
            .filter(m -> m.getCourse() != null && m.getCourse().getId() == courseId)
            .collect(Collectors.toList());
    }
}
