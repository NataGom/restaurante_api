package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Tuition;
import co.edu.umanizales.restaurante_api.model.Student;
import co.edu.umanizales.restaurante_api.model.Course;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TuitionRepository extends CsvRepository<Tuition> {
    
    private static final String FILE_PATH = "data/tuitions.csv";

    public TuitionRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,studentId,courseId,enrollmentDate,status,period";
    }

    @Override
    protected String toCsv(Tuition tuition) {
        long studentId = tuition.getStudent() != null ? tuition.getStudent().getId() : 0;
        long courseId = tuition.getCourse() != null ? tuition.getCourse().getId() : 0;
        
        return String.join(",",
            String.valueOf(tuition.getId()),
            studentId != 0 ? String.valueOf(studentId) : "",
            courseId != 0 ? String.valueOf(courseId) : "",
            tuition.getEnrollmentDate() != null ? tuition.getEnrollmentDate().toString() : "",
            escapeCsv(tuition.getStatus()),
            escapeCsv(tuition.getPeriod())
        );
    }

    @Override
    protected Tuition fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Tuition tuition = new Tuition();
        tuition.setId(Long.parseLong(fields[0]));
        
        // Load student ID and create placeholder
        if (!fields[1].isEmpty()) {
            long studentId = Long.parseLong(fields[1]);
            Student student = new Student();
            student.setId(studentId);
            tuition.setStudent(student);
        }
        
        // Load course ID and create placeholder
        if (!fields[2].isEmpty()) {
            long courseId = Long.parseLong(fields[2]);
            Course course = new Course();
            course.setId(courseId);
            tuition.setCourse(course);
        }
        
        if (!fields[3].isEmpty()) {
            tuition.setEnrollmentDate(LocalDate.parse(fields[3]));
        }
        tuition.setStatus(unescapeCsv(fields[4]));
        tuition.setPeriod(unescapeCsv(fields[5]));
        
        return tuition;
    }

    @Override
    protected long getId(Tuition tuition) {
        return tuition.getId();
    }

    @Override
    protected void setId(Tuition tuition, long id) {
        tuition.setId(id);
    }

    /**
     * Find tuitions by student ID
     */
    public List<Tuition> findByStudentId(long studentId) {
        List<Tuition> result = new ArrayList<>();
        List<Tuition> all = findAll();
        for (Tuition t : all) {
            if (t.getStudent() != null && t.getStudent().getId() == studentId) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Find tuitions by course ID
     */
    public List<Tuition> findByCourseId(long courseId) {
        List<Tuition> result = new ArrayList<>();
        List<Tuition> all = findAll();
        for (Tuition t : all) {
            if (t.getCourse() != null && t.getCourse().getId() == courseId) {
                result.add(t);
            }
        }
        return result;
    }
}
