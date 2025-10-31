package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Curso;
import co.edu.umanizales.restaurante_api.model.Modalidad;
import org.springframework.stereotype.Repository;

@Repository
public class CursoRepository extends CsvRepository<Curso> {
    
    private static final String FILE_PATH = "data/cursos.csv";

    public CursoRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,codigo,nombre,creditos,modalidad,profesorId";
    }

    @Override
    protected String toCsv(Curso curso) {
        Long profesorId = curso.getProfesor() != null ? curso.getProfesor().getId() : null;
        return String.join(",",
            String.valueOf(curso.getId()),
            escapeCsv(curso.getCodigo()),
            escapeCsv(curso.getNombre()),
            String.valueOf(curso.getCreditos()),
            curso.getModalidad() != null ? curso.getModalidad().name() : "",
            profesorId != null ? String.valueOf(profesorId) : ""
        );
    }

    @Override
    protected Curso fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Curso curso = new Curso();
        curso.setId(Long.parseLong(fields[0]));
        curso.setCodigo(unescapeCsv(fields[1]));
        curso.setNombre(unescapeCsv(fields[2]));
        curso.setCreditos(Integer.parseInt(fields[3]));
        
        if (!fields[4].isEmpty()) {
            curso.setModalidad(Modalidad.valueOf(fields[4]));
        }
        
        // Profesor will be loaded separately in service layer
        
        return curso;
    }

    @Override
    protected Long getId(Curso curso) {
        return curso.getId();
    }

    @Override
    protected void setId(Curso curso, Long id) {
        curso.setId(id);
    }
}
