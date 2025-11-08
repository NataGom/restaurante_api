package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherRepository extends CsvRepository<Teacher> {
    
    private static final String FILE_PATH = "data/teachers.csv";

    public TeacherRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,name,email,phone,specialty,yearsOfExperience";
    }

    @Override
    protected String toCsv(Teacher teacher) {
        return String.join(",",
            String.valueOf(teacher.getId()),
            escapeCsv(teacher.getName()),
            escapeCsv(teacher.getEmail()),
            escapeCsv(teacher.getPhone()),
            escapeCsv(teacher.getSpecialty()),
            String.valueOf(teacher.getYearsOfExperience())
        );
    }

    @Override
    protected Teacher fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Teacher teacher = new Teacher();
        teacher.setId(Long.parseLong(fields[0]));
        teacher.setName(unescapeCsv(fields[1]));
        teacher.setEmail(unescapeCsv(fields[2]));
        teacher.setPhone(unescapeCsv(fields[3]));
        teacher.setSpecialty(unescapeCsv(fields[4]));
        teacher.setYearsOfExperience(Integer.parseInt(fields[5]));
        
        return teacher;
    }

    @Override
    protected long getId(Teacher teacher) {
        return teacher.getId();
    }

    @Override
    protected void setId(Teacher teacher, long id) {
        teacher.setId(id);
    }
}
