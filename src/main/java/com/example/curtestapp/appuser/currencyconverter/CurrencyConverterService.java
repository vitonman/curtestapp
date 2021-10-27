package com.example.curtestapp.appuser.currencyconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class CurrencyConverterService {

    private final CurrencyConverterReposirory currencyConverterReposirory;

    @Autowired
    public CurrencyConverterService(CurrencyConverterReposirory currencyConverterReposirory) {
        this.currencyConverterReposirory = currencyConverterReposirory;
    }


    @GetMapping
    public List<CurrencyConverter> getCurrencyConverterLog(){
        return currencyConverterReposirory.findAll();
    }


    public List<CurrencyConverter> getUserCurrencyConverterLog(Long user_id){

        return currencyConverterReposirory.findCurrencyLogsByUserId(user_id);
    }

    public CurrencyConverter getCurrencyConverterLogById(Long id){

        return currencyConverterReposirory.findCurrencyLogById(id);
    }




    public void addNewConverterLog(CurrencyConverter currencyConverter) {


            currencyConverterReposirory.save(currencyConverter);


    }


}
