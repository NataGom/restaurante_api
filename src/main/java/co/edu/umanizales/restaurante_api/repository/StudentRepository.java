package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository extends CsvRepository<Student> {
    
    private static final String FILE_PATH = "data/students.csv";

    public StudentRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,name,email,phone,studentId,major,semester";
    }

    @Override
    protected String toCsv(Student student) {
        return String.join(",",
            String.valueOf(student.getId()),
            escapeCsv(student.getName()),
            escapeCsv(student.getEmail()),
            escapeCsv(student.getPhone()),
            escapeCsv(student.getStudentId()),
            escapeCsv(student.getMajor()),
            String.valueOf(student.getSemester())
        );
    }

    @Override
    protected Student fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Student student = new Student();
        student.setId(Long.parseLong(fields[0]));
        student.setName(unescapeCsv(fields[1]));
        student.setEmail(unescapeCsv(fields[2]));
        student.setPhone(unescapeCsv(fields[3]));
        student.setStudentId(unescapeCsv(fields[4]));
        student.setMajor(unescapeCsv(fields[5]));
        student.setSemester(Integer.parseInt(fields[6]));
        
        return student;
    }

    @Override
    protected long getId(Student student) {
        return student.getId();
    }

    @Override
    protected void setId(Student student, long id) {
        student.setId(id);
    }
}
