package events.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Tag extends AbstractEntity{

    @Size(min = 1, max = 3)
    @NotBlank
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
