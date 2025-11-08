package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectRepository extends CsvRepository<Subject> {
    
    private static final String FILE_PATH = "data/subjects.csv";

    public SubjectRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,code,name,description,credits";
    }

    @Override
    protected String toCsv(Subject subject) {
        return String.join(",",
            String.valueOf(subject.getId()),
            escapeCsv(subject.getCode()),
            escapeCsv(subject.getName()),
            escapeCsv(subject.getDescription()),
            String.valueOf(subject.getCredits())
        );
    }

    @Override
    protected Subject fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Subject subject = new Subject();
        subject.setId(Long.parseLong(fields[0]));
        subject.setCode(unescapeCsv(fields[1]));
        subject.setName(unescapeCsv(fields[2]));
        subject.setDescription(unescapeCsv(fields[3]));
        subject.setCredits(Integer.parseInt(fields[4]));
        
        return subject;
    }

    @Override
    protected long getId(Subject subject) {
        return subject.getId();
    }

    @Override
    protected void setId(Subject subject, long id) {
        subject.setId(id);
    }
}
