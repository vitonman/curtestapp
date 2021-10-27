package com.example.curtestapp.appuser.currencyconverter;

import javax.persistence.*;

@Entity
@Table
public class CurrencyConverter {

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
    private Long user_id;
    private String date;
    private String fromCurrency;
    private String toCurrency;
    private Double value;
    private Double valueResult;

    public CurrencyConverter(Long user_id, String date, String fromCurrency, String toCurrency, Double value, Double valueResult) {
        this.user_id = user_id;
        this.date = date;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.value = value;
        this.valueResult = valueResult;
    }

    public CurrencyConverter(){

    }

    public Long getId() {
        return id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValueResult() {
        return valueResult;
    }

    public void setValueResult(Double valueResult) {
        this.valueResult = valueResult;
    }

    @Override
    public String toString() {
        return "CurrencyConverter{" +
                "id=" + id +
                ", userId=" + user_id +
                ", date='" + date + '\'' +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", value='" + value + '\'' +
                ", valueResult='" + valueResult + '\'' +
                '}';
    }
}
