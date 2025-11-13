package co.edu.umanizales.restaurante_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private long id;
    private String code;
    private String name;
    private String description;
    private int credits;
    
    /**
     * Gets formatted information about the subject
     * @return formatted string with subject details
     */
    public String getInformation() {
        return String.format("%s - %s: %s (Credits: %d)",
                code != null ? code : "N/A",
                name != null ? name : "N/A",
                description != null ? description : "No description",
                credits
        );
    }
}
