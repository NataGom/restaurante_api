package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private int creditos;
}
