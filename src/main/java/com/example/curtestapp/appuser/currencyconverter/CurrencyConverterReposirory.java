package com.example.curtestapp.appuser.currencyconverter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyConverterReposirory extends JpaRepository<CurrencyConverter, Long> {


    @Query("SELECT l FROM CurrencyConverter l WHERE l.id = ?1")
    CurrencyConverter findCurrencyLogById(Long user_id);


    @Query("SELECT l FROM CurrencyConverter l WHERE l.user_id  = ?1")
    List<CurrencyConverter> findCurrencyLogsByUserId(Long user_id);



}
