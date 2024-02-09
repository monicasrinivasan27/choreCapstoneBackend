package org.launchcode.taskcrusher.models;


import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "holiday")
public class Holiday {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "holidayDate")
    private LocalDate holidayDate;

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }
    public Holiday() {
    }

    public Holiday(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }
}
