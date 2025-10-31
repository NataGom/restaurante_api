package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Profesor;
import org.springframework.stereotype.Repository;

@Repository
public class ProfesorRepository extends CsvRepository<Profesor> {
    
    private static final String FILE_PATH = "data/profesores.csv";

    public ProfesorRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,nombre,email,telefono,especialidad,antiguedad";
    }

    @Override
    protected String toCsv(Profesor profesor) {
        return String.join(",",
            String.valueOf(profesor.getId()),
            escapeCsv(profesor.getNombre()),
            escapeCsv(profesor.getEmail()),
            escapeCsv(profesor.getTelefono()),
            escapeCsv(profesor.getEspecialidad()),
            String.valueOf(profesor.getAntiguedad())
        );
    }

    @Override
    protected Profesor fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Profesor profesor = new Profesor();
        profesor.setId(Long.parseLong(fields[0]));
        profesor.setNombre(unescapeCsv(fields[1]));
        profesor.setEmail(unescapeCsv(fields[2]));
        profesor.setTelefono(unescapeCsv(fields[3]));
        profesor.setEspecialidad(unescapeCsv(fields[4]));
        profesor.setAntiguedad(Integer.parseInt(fields[5]));
        
        return profesor;
    }

    @Override
    protected Long getId(Profesor profesor) {
        return profesor.getId();
    }

    @Override
    protected void setId(Profesor profesor, Long id) {
        profesor.setId(id);
    }
}
