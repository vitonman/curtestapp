package com.example.curtestapp.appuser.currencyconverter;

import com.example.curtestapp.appuser.AppUser;
import com.example.curtestapp.appuser.currencies.Currency;
import com.example.curtestapp.appuser.currencies.CurrencyService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/currencyconverter")
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;
    private final CurrencyService currencyService;

    //catch user id


    public CurrencyConverterController(CurrencyConverterService currencyConverterService, CurrencyService currencyService) {
        this.currencyConverterService = currencyConverterService;
        this.currencyService = currencyService;
    }

    @GetMapping(path = ("/getAllCurrencyLog"))
    public List<CurrencyConverter> getCurrencies(){
        return currencyConverterService.getCurrencyConverterLog();
    }



    @PostMapping(path = ("/insertLogInformation/{date}/{fromCurrency}/{toCurrency}/{fromValue}/{toValue}"))
    public void insertLogInformationAboutCurrencyConvert(
            @PathVariable("date")String date,
            @PathVariable("fromCurrency")String fromCurrency,
            @PathVariable("toCurrency")String toCurrency,
            @PathVariable("fromValue")Double fromValue,
            @PathVariable("toValue")Double toValue
    ){

        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CurrencyConverter currencyConverterFirstLog = new CurrencyConverter(
                appUser.getId(),
                date,
                fromCurrency,
                toCurrency,
                fromValue,
                toValue);

        currencyConverterService.addNewConverterLog(currencyConverterFirstLog);

    }

    @GetMapping(path = ("{id}"))
    public CurrencyConverter getUserById(Long id){
        return currencyConverterService.getCurrencyConverterLogById(id);
    }



    @RequestMapping("/getCurrentUserCurrencyLog")
    public String getLogInformationByUserId(Model model){
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CurrencyConverter> userCurrenciesLog = currencyConverterService.getUserCurrencyConverterLog(appUser.getId());
        model.addAttribute("usercurrencylog", userCurrenciesLog);
        return "pages/usercurrencylog";


    }

 /*   @PostMapping("/doConvert")
    public String convertChoosenCurrencyToAnother(String fromCurrency, String toCurrency, Double value){

        Currency currency1 = currencyService.getCurrencyByName(fromCurrency);
        Currency currency2 = currencyService.getCurrencyByName(toCurrency);//10.0145

        // exchange rate
        Double currencyRate1 = currency1.getRate();//1.1637
        Double currencyRate2 = currency2.getRate();

        //EXAMPLE 20$ to eur. after that EUR to sek.
        Double eurValue1 = (value / currencyRate1);//17€

        System.out.println("===================================================================");
        System.out.println(eurValue1 + "VALUE");

        insertLogInformationAboutCurrencyConvert(
                java.time.LocalDate.now().toString(), fromCurrency, toCurrency,value,
                eurValue1*currencyRate2);


        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(eurValue1 * currencyRate2);


    }*/





    @PostMapping("/doConvert/{fromCurrency}/{toCurrency}/{value}")
    public String convertChoosenCurrencyToAnother(@PathVariable("fromCurrency") String fromCurrency,
                                                  @PathVariable("toCurrency") String toCurrency,
                                                  @PathVariable("value") Double value){

        Currency currency1 = currencyService.getCurrencyByName(fromCurrency);
        Currency currency2 = currencyService.getCurrencyByName(toCurrency);//10.0145

        // exchange rate
        Double currencyRate1 = currency1.getRate();//1.1637
        Double currencyRate2 = currency2.getRate();

        //EXAMPLE 20$ to eur. after that EUR to sek.
        Double eurValue1 = (value / currencyRate1);//17€

        System.out.println("===================================================================");
        System.out.println(eurValue1 + "VALUE");

        insertLogInformationAboutCurrencyConvert(
                java.time.LocalDate.now().toString(), fromCurrency, toCurrency,value,
                eurValue1*currencyRate2);


        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(eurValue1 * currencyRate2);


    }



/*
    @GetMapping("/currenciesnames")
    public String getCurrenciesNames(Model model){

        List<Currency> currencies = currencyService.getCurrencies();
        List<String> curreniesNames = new ArrayList<>();

        for (Currency currency : currencies) {
            curreniesNames.add(currency.getCurrencyname());
        }

        model.addAttribute("currencyconverternames", curreniesNames);

        return "pages/currencyconverter";

    }*/





}
