package org.launchcode.taskcrusher.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.launchcode.taskcrusher.enums.ChoreStatus;

import java.time.LocalDate;

@Entity
@Table(name = "chores")
public class Chore {

    @Id
    @GeneratedValue
    private int choreId;

    private String name;

    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "image")
    private String image;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "value")
    private int value;

    @ManyToOne
    @JoinColumn(name = "kid_id")
    private Kid kid;

    @ManyToOne
    @JoinColumn(name = "parentId")
    private User parent;


    @Enumerated(EnumType.STRING)
    private ChoreStatus status;


    public int getChoreId() {
        return choreId;
    }

    public void setChoreId(int choreId) {
        this.choreId = choreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Kid getKid() {
        return kid;
    }

    public void setKid(Kid kid) {
        this.kid = kid;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ChoreStatus getStatus() {
        return status;
    }

    public void setStatus(ChoreStatus status) {
        this.status = status;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Chore{" +
                "choreId=" + choreId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", kid=" + kid +
                '}';
    }
}
