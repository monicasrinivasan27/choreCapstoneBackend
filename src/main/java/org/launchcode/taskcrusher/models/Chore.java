package org.launchcode.taskcrusher.models;

import jakarta.persistence.*;

@Entity
@Table(name = "chores")
public class Chore {

    @Id
    @GeneratedValue
    private int choreId;

    private String name;

    private String description;

    @Column(name = "image")
    private String image;

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
}
