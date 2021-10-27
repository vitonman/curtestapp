package com.example.curtestapp.appuser.currencies;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;
    private static final String CURRENCY = "currency";
    private static final String CUBE_NODE_1 = "//Cube/Cube";
    private static final String CUBE_NODE_2 = "//Cube/Cube/Cube";
    private static final String RATE = "rate";
    private static final String DATE = "time";



    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @RequestMapping("/getAll")
    public String getCurrencies(Model model){

        List<Currency> currencies = currencyService.getCurrencies();

        model.addAttribute("currencies", currencies);

        return "pages/currencies";

    }

/*
    @GetMapping("/getAllText")
    public List<Currency> getCurrenciesTest(){

        List<Currency> currencies = currencyService.getCurrencies();

        return  currencies;

    }
*/

    @GetMapping(path = ("{id}"))
    public Currency getUserById(Long id){

        return currencyService.getById(id);

    }




    @PostMapping(path = ("/loadNewData"))
    public void downloadNewCurrencyDB(){
        //======================================================================
        //==========================DATE NOW====================================
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();//2021-10-15
        System.out.println(formatter.format(now)+" CURRENT DATE");
        //==========================DATE NOW====================================
        //======================================================================

        List<Currency> currRateList = new ArrayList<>();

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        //==========================DATE CHECKER================================
        //if date not match then load new to db
        Document document = null;
        String spec = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

        try{
            URL url = new URL(spec);
            InputStream is = url.openStream();
            document = builder.parse(is);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            //================================
            //DATE TAKE FROM NODE (first massive)
            XPathExpression datecheck = xpath.compile(CUBE_NODE_1);
            Node chk = (Node) datecheck.evaluate(document, XPathConstants.NODE);
            NamedNodeMap attributes_1 = chk.getAttributes();
            Node dateEUbankXML = attributes_1.getNamedItem(DATE);
            String dateEUtext = dateEUbankXML.getNodeValue();  ///<- last date from site
            //=================================
            LocalDate dateFromEUbank = LocalDate.parse(dateEUtext, formatter);

            System.out.println("LOG DATE ----->     " + dateEUtext);

            if(!dateFromEUbank.isBefore(ChronoLocalDate.from(now)) &&
                    !dateFromEUbank.isEqual(ChronoLocalDate.from(now))
                    || currencyService.getCurrencies().isEmpty()){

                System.out.println("DOWNLOADED NEW DATA FROM EU BANK");

                XPathExpression expr = xpath.compile(CUBE_NODE_2);
                NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
                for (int i = 0; i < nl.getLength(); i++) {
                    Node node = nl.item(i);
                    NamedNodeMap attribs = node.getAttributes();
                    if (attribs.getLength() > 0) {
                        Node currencyAttrib = attribs.getNamedItem(CURRENCY);
                        if (currencyAttrib != null) {
                            String currencyTxt = currencyAttrib.getNodeValue();
                            String rateTxt = attribs.getNamedItem(RATE).getNodeValue();

                            Double rateNum = Double.parseDouble(rateTxt);


                            currRateList.add(new Currency(currencyTxt, rateNum, dateEUtext));
                        }
                    }
                }
                currRateList.add(new Currency("EUR", 1.0, dateEUtext));

                currencyService.deleteAll();
                currencyService.saveAll(currRateList);


            }else{

                System.out.println("DATE LAST CHANGES: -> NOT UPDATED: " +
                        "Not finded new data from EU BANK, last update --> " + dateEUtext);
                System.out.println(dateEUtext);
            }


        }catch(SAXException | IOException | XPathExpressionException e){
            e.printStackTrace();
        }



    }


}
