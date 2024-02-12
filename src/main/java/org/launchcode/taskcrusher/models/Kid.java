package org.launchcode.taskcrusher.models;

import jakarta.persistence.*;

@Entity
public class Kid {

    @Id
    @GeneratedValue
    private int kidId;

    private String name;

    private int points;

    private double dollars;

    @ManyToOne
    @JoinColumn(name = "id")
    private User parent;



    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Kid{" +
                "kidId=" + kidId +
                ", name='" + name + '\'' +
                '}';
    }
}