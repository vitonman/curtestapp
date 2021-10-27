package com.example.curtestapp.appuser.currencies;


import com.example.curtestapp.appuser.currencies.date.DateCheck;
import com.example.curtestapp.appuser.currencies.date.DateCheckRepository;
import com.example.curtestapp.appuser.currencies.date.DateCheckService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class CurrencyXMLconfig {


    private static final String CURRENCY = "currency";
    private static final String CUBE_NODE_1 = "//Cube/Cube";
    private static final String CUBE_NODE_2 = "//Cube/Cube/Cube";
    private static final String RATE = "rate";
    private static final String DATE = "time";
    private final CurrencyService currencyService;


    public CurrencyXMLconfig(CurrencyService currencyService) {
        this.currencyService = currencyService;

    }

    @Bean
    CommandLineRunner commandLineRunner(CurrencyRepository repository, DateCheckRepository dateRepository) {

        return args -> {
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

                    repository.deleteAll();
                    repository.saveAll(currRateList);


                }else{

                    System.out.println("DATE LAST CHANGES: -> NOT UPDATED: " +
                            "Not finded new data from EU BANK, last update --> " + dateEUtext);
                    System.out.println(dateEUtext);
                }


                }catch(SAXException | IOException | XPathExpressionException e){
                    e.printStackTrace();
                }


        };

    }

    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

}
