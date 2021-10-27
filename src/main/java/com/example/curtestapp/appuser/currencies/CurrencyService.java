package com.example.curtestapp.appuser.currencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }


    public List<Currency> getCurrencies(){
        return currencyRepository.findAll();
    }

    public void addNewCurrency(Currency currency) {
        Optional<Currency> currencyData = currencyRepository.findCurrencyByDate(currency.getDate());

        if(currencyData.isPresent()){
            throw  new IllegalStateException("WRONG");
        }else{
            currencyRepository.save(currency);
        }
        //System.out.println(userman);
    }

    public Currency getById(Long id){

        return getById(id);

    }

    public void deleteAll(){

        currencyRepository.deleteAll();
    }

    public List<Currency> saveAll(Iterable<Currency> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<Currency> result = new ArrayList();
        Iterator var3 = entities.iterator();

        while(var3.hasNext()) {
            Currency entity = (Currency) var3.next();
            result.add(this.currencyRepository.save(entity));
        }

        return result;
    }

    public Currency getCurrencyByName(String name){

        return currencyRepository.findCurrencyByName(name);

    }

    public Boolean hasSameDate(String date){

       if(currencyRepository.findCurrencyByDate(date).isEmpty()){

           return false;

       }else{
           return true;
       }

    }



/*
    public List<String> getAllCurrencyNames(){

        return currencyRepository.selectCurrencyNamesList();

    }
*/

}
