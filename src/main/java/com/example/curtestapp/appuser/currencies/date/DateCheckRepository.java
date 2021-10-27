package com.example.curtestapp.appuser.currencies.date;


import com.example.curtestapp.appuser.currencies.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateCheckRepository extends JpaRepository<DateCheck, Long> {



}
