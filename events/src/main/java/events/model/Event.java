package events.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Event extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters!")
    private String name;

    @ManyToOne
    @NotNull(message = "Category is required")
    private EventCategory category;

    public Event(String name, EventCategory category) {
        this.name = name;
        this.category = category;
    }

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory eventCategory) {
        this.category = eventCategory;
    }

    public EventDetails getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(EventDetails eventDetails) {
        this.eventDetails = eventDetails;
    }

    @Override
    public String toString() {
        return name;
    }
}
