package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Estudiante;
import org.springframework.stereotype.Repository;

@Repository
public class EstudianteRepository extends CsvRepository<Estudiante> {
    
    private static final String FILE_PATH = "data/estudiantes.csv";

    public EstudianteRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,nombre,email,telefono,matricula,carrera,semestre";
    }

    @Override
    protected String toCsv(Estudiante estudiante) {
        return String.join(",",
            String.valueOf(estudiante.getId()),
            escapeCsv(estudiante.getNombre()),
            escapeCsv(estudiante.getEmail()),
            escapeCsv(estudiante.getTelefono()),
            escapeCsv(estudiante.getMatricula()),
            escapeCsv(estudiante.getCarrera()),
            String.valueOf(estudiante.getSemestre())
        );
    }

    @Override
    protected Estudiante fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Estudiante estudiante = new Estudiante();
        estudiante.setId(Long.parseLong(fields[0]));
        estudiante.setNombre(unescapeCsv(fields[1]));
        estudiante.setEmail(unescapeCsv(fields[2]));
        estudiante.setTelefono(unescapeCsv(fields[3]));
        estudiante.setMatricula(unescapeCsv(fields[4]));
        estudiante.setCarrera(unescapeCsv(fields[5]));
        estudiante.setSemestre(Integer.parseInt(fields[6]));
        
        return estudiante;
    }

    @Override
    protected Long getId(Estudiante estudiante) {
        return estudiante.getId();
    }

    @Override
    protected void setId(Estudiante estudiante, Long id) {
        estudiante.setId(id);
    }
}
