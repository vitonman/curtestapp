package com.example.curtestapp.appuser.currencies;

import javax.persistence.*;

@Entity
@Table
public class Currency {

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

    private String currencyname;
    private Double rate;
    private String date;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Currency(String currencyname, Double rate, String date) {

        this.currencyname = currencyname;
        this.rate = rate;
        this.date = date;
    }

    public Currency() {


    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setName(String currencyname) {
        this.currencyname = currencyname;
    }


    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", currency='" + currencyname + '\'' +
                ", rate='" + rate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
