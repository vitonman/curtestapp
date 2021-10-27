package com.example.curtestapp.appuser.currencies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {


    @Query("SELECT s FROM Currency s WHERE s.currencyname = ?1")
    Currency findCurrencyByName(String currencyname);

    @Query("SELECT s FROM Currency s WHERE s.date = ?1")
    Optional<Currency> findCurrencyByDate(String currencydate);

    @Override
    Optional<Currency> findById(Long aLong);
//SELECT u FROM Currency Where u.currencyname




}
