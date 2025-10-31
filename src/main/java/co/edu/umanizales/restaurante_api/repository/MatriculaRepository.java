package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Matricula;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MatriculaRepository extends CsvRepository<Matricula> {
    
    private static final String FILE_PATH = "data/matriculas.csv";

    public MatriculaRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,estudianteId,cursoId,fechaMatricula,estado,periodo";
    }

    @Override
    protected String toCsv(Matricula matricula) {
        Long estudianteId = matricula.getEstudiante() != null ? matricula.getEstudiante().getId() : null;
        Long cursoId = matricula.getCurso() != null ? matricula.getCurso().getId() : null;
        
        return String.join(",",
            String.valueOf(matricula.getId()),
            estudianteId != null ? String.valueOf(estudianteId) : "",
            cursoId != null ? String.valueOf(cursoId) : "",
            matricula.getFechaMatricula() != null ? matricula.getFechaMatricula().toString() : "",
            escapeCsv(matricula.getEstado()),
            escapeCsv(matricula.getPeriodo())
        );
    }

    @Override
    protected Matricula fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Matricula matricula = new Matricula();
        matricula.setId(Long.parseLong(fields[0]));
        
        // IDs will be resolved in service layer
        
        if (!fields[3].isEmpty()) {
            matricula.setFechaMatricula(LocalDate.parse(fields[3]));
        }
        matricula.setEstado(unescapeCsv(fields[4]));
        matricula.setPeriodo(unescapeCsv(fields[5]));
        
        return matricula;
    }

    @Override
    protected Long getId(Matricula matricula) {
        return matricula.getId();
    }

    @Override
    protected void setId(Matricula matricula, Long id) {
        matricula.setId(id);
    }

    /**
     * Find matriculas by estudiante ID
     */
    public List<Matricula> findByEstudianteId(Long estudianteId) {
        return findAll().stream()
            .filter(m -> m.getEstudiante() != null && m.getEstudiante().getId().equals(estudianteId))
            .collect(Collectors.toList());
    }

    /**
     * Find matriculas by curso ID
     */
    public List<Matricula> findByCursoId(Long cursoId) {
        return findAll().stream()
            .filter(m -> m.getCurso() != null && m.getCurso().getId().equals(cursoId))
            .collect(Collectors.toList());
    }
}
