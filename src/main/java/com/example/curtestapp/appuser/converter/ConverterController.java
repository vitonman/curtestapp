package com.example.curtestapp.appuser.converter;


import com.example.curtestapp.appuser.currencies.Currency;
import com.example.curtestapp.appuser.currencies.CurrencyController;
import com.example.curtestapp.appuser.currencies.CurrencyService;
import com.example.curtestapp.appuser.currencyconverter.CurrencyConverterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/converter")
public class ConverterController {

    private final CurrencyService currencyService;
    private final CurrencyConverterController currencyConverterController;
    private final CurrencyController currencyController;
    private List<Double> currencyValueAnswer = new ArrayList<>();

    @Autowired
    public ConverterController(CurrencyService currencyService, CurrencyConverterController currencyConverterController, CurrencyController currencyController) {
        this.currencyService = currencyService;
        this.currencyConverterController = currencyConverterController;
        this.currencyController = currencyController;
    }


    @RequestMapping("/getnames")
    public String getnames(Model model){

        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute("currencyValueAnswers", currencyValueAnswer);
        model.addAttribute("currencynames", currencies);

        return "pages/currencyconverter";

    }

    @PostMapping("/doConvert")
    public String convertChoosenCurrencyToAnother(Model model,
                                                  @RequestParam String fromCurrency,
                                                  @RequestParam String toCurrency,
                                                  @RequestParam Double value,
                                                  @RequestParam String lastUpdate
    ) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateFromDB = LocalDate.parse(lastUpdate, formatter);

        LocalDateTime now = LocalDateTime.now();//2021-10-15

        if(!dateFromDB.isBefore(ChronoLocalDate.from(now)) &&
                !dateFromDB.isEqual(ChronoLocalDate.from(now))
                || currencyService.getCurrencies().isEmpty()){


            System.out.println("CHECKING FOR NEW DATA FROM EU BANK");
            currencyController.downloadNewCurrencyDB();

        }else{

            System.out.println("UP To date, no need to update");


        }

        Currency currency1 = currencyService.getCurrencyByName(fromCurrency);
        Currency currency2 = currencyService.getCurrencyByName(toCurrency);//10.0145
        // exchange rate
        Double currencyRate1 = currency1.getRate();//1.1637
        Double currencyRate2 = currency2.getRate();

        //EXAMPLE 20$ to eur. after that EUR to sek.
        Double eurValue1 = (value / currencyRate1);//17€

        System.out.println("===================================================================");
        System.out.println(eurValue1 + "VALUE");

        currencyConverterController.insertLogInformationAboutCurrencyConvert(
                java.time.LocalDate.now().toString(), fromCurrency, toCurrency,value,
                eurValue1*currencyRate2);


        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println(df.format(eurValue1 * currencyRate2));


        model.addAttribute("currencyValueAnswer",df.format(eurValue1 * currencyRate2));
        //return ;
        currencyValueAnswer.add(Double.valueOf(df.format(eurValue1 * currencyRate2)));


        System.out.println("SUCCESSS SUCCESS");
        return "redirect:/converter/getnames";
    }





}
