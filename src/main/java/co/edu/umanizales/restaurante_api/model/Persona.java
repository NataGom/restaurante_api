package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Persona {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    /**
     * Método abstracto que debe ser implementado por las subclases
     * para retornar el rol específico de la persona
     */
    public abstract String getRol();

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol='" + getRol() + '\'' +
                '}';
    }
}
