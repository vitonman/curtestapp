package com.example.curtestapp.appuser.currencies.date;

import javax.persistence.*;

@Entity
@Table
public class DateCheck {



    @Id
    @SequenceGenerator(
            name = "users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;
    private String date;

    public DateCheck(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DateCheck{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
