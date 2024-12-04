package com.betrybe.todobackend.models.entities;

import jakarta.persistence.*;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Boolean checked = false;

    public Task(Long id, String description, boolean checked) {
        this.id = id;
        this.description = description;
        this.checked = checked;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
