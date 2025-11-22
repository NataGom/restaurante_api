package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Course;
import co.edu.umanizales.restaurante_api.model.Mode;
import co.edu.umanizales.restaurante_api.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository extends CsvRepository<Course> {
    
    private static final String FILE_PATH = "data/courses.csv";

    public CourseRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,code,name,credits,semester,mode,teacherId";
    }

    @Override
    protected String toCsv(Course course) {
        long teacherId = course.getTeacher() != null ? course.getTeacher().getId() : 0;
        return String.join(",",
            String.valueOf(course.getId()),
            escapeCsv(course.getCode()),
            escapeCsv(course.getName()),
            String.valueOf(course.getCredits()),
            String.valueOf(course.getSemester()),
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
        
        if (!fields[3].isEmpty()) {
            course.setCredits(Integer.parseInt(fields[3]));
        }
        
        if (fields.length > 4 && !fields[4].isEmpty()) {
            course.setSemester(Integer.parseInt(fields[4]));
        }
        
        if (fields.length > 5 && !fields[5].isEmpty()) {
            course.setMode(Mode.valueOf(fields[5]));
        }
        
        // Load teacher ID and create placeholder
        if (fields.length > 6 && !fields[6].isEmpty()) {
            long teacherId = Long.parseLong(fields[6]);
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            course.setTeacher(teacher);
        }
        
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
