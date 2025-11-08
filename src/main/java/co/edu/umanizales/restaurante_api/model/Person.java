package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private long id;
    private String name;
    private String email;
    private String phone;

    /**
     * Abstract method to be implemented by subclasses
     * to return the specific role of the person
     */
    public abstract String getRole();

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}
