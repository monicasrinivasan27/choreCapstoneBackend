package org.launchcode.taskcrusher.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Kid {

    @Id
    @GeneratedValue
    private int kidId;

    private String name;

    private String username;

    private String password;

    private int points;

    private double dollars;

    public Kid(int kidId, String name, String username, String password, int points, double dollars) {
        this.kidId = kidId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.points = points;
        this.dollars = dollars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public String toString() {
        return "Kid{" +
                "kidId=" + kidId +
                ", name='" + name + '\'' +
                '}';
    }
}