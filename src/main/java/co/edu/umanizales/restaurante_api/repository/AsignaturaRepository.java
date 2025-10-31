package co.edu.umanizales.restaurante_api.repository;

import co.edu.umanizales.restaurante_api.model.Asignatura;
import org.springframework.stereotype.Repository;

@Repository
public class AsignaturaRepository extends CsvRepository<Asignatura> {
    
    private static final String FILE_PATH = "data/asignaturas.csv";

    public AsignaturaRepository() {
        super(FILE_PATH);
    }

    @Override
    protected String getHeaders() {
        return "id,codigo,nombre,descripcion,creditos";
    }

    @Override
    protected String toCsv(Asignatura asignatura) {
        return String.join(",",
            String.valueOf(asignatura.getId()),
            escapeCsv(asignatura.getCodigo()),
            escapeCsv(asignatura.getNombre()),
            escapeCsv(asignatura.getDescripcion()),
            String.valueOf(asignatura.getCreditos())
        );
    }

    @Override
    protected Asignatura fromCsv(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        
        Asignatura asignatura = new Asignatura();
        asignatura.setId(Long.parseLong(fields[0]));
        asignatura.setCodigo(unescapeCsv(fields[1]));
        asignatura.setNombre(unescapeCsv(fields[2]));
        asignatura.setDescripcion(unescapeCsv(fields[3]));
        asignatura.setCreditos(Integer.parseInt(fields[4]));
        
        return asignatura;
    }

    @Override
    protected Long getId(Asignatura asignatura) {
        return asignatura.getId();
    }

    @Override
    protected void setId(Asignatura asignatura, Long id) {
        asignatura.setId(id);
    }
}
