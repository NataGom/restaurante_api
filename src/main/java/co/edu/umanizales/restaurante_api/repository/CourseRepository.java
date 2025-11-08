package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Mode;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository extends CsvRepository<Course> {
    
    private static final String FILE_PATH = "data/courses.csv";

    public CourseRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,code,name,credits,mode,teacherId";
    }

    @Override
    protected String toCsv(Course course) {
        long teacherId = course.getTeacher() != null ? course.getTeacher().getId() : 0;
        return String.join(",",
            String.valueOf(course.getId()),
            escapeCsv(course.getCode()),
            escapeCsv(course.getName()),
            String.valueOf(course.getCredits()),
            course.getMode() != null ? course.getMode().name() : "",
            teacherId != 0 ? String.valueOf(teacherId) : ""
        );
    }

    @Override
    protected Course fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Course course = new Course();
        course.setId(Long.parseLong(fields[0]));
        course.setCode(unescapeCsv(fields[1]));
        course.setName(unescapeCsv(fields[2]));
        course.setCredits(Integer.parseInt(fields[3]));
        
        if (!fields[4].isEmpty()) {
            course.setMode(Mode.valueOf(fields[4]));
        }
        
        // Teacher will be loaded separately in service layer
        
        return course;
    }

    @Override
    protected long getId(Course course) {
        return course.getId();
    }

    @Override
    protected void setId(Course course, long id) {
        course.setId(id);
    }
}
