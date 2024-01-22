package org.launchcode.taskcrusher.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    public AbstractEntity() {}

    public int getId() {
        return id;
    }
}
