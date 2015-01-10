package ru.multicabinet.currency.defaultcurrencymodules;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.multicabinet.module.CurrencyRateModuleData;
import ru.multicabinet.module.api.CurrencyRateModule;
import ru.multicabinet.module.option.template.ModuleOptionTemplate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.*;import java.lang.Exception;import java.lang.Integer;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.System;import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 1/5/15
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CbrCurrencyService implements CurrencyRateModule, BundleActivator {
    @Override
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap();
        info.put("name", "cbr");
        info.put("desc", "Central Bank of Russia");
        info.put("version", "0.3");
        info.put("currency", "RUB");
        return info;
    }

    @Override
    public List<ModuleOptionTemplate> getRequiredModuleOptions() {
        List<ModuleOptionTemplate> moduleOptionTemplates = new ArrayList<ModuleOptionTemplate>();
        return moduleOptionTemplates;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, BigDecimal> getCurrenciesRates(String baseCurrency, CurrencyRateModuleData currencyRateModuleData) {
        if (!getSupportedCurrencies().contains(baseCurrency.toUpperCase())){
            return null;
        }
        Map<String, BigDecimal> baseRates = getRates();
        Map<String, BigDecimal> convertedRates = new HashMap<String, BigDecimal>();
        baseRates.put((String)getInfo().get("currency"), new BigDecimal(1));
        if (getInfo().get("currency").equals(baseCurrency.toUpperCase())){
            convertedRates = baseRates;
        } else {
            for (String currency : baseRates.keySet()){
                //System.out.println("Dividing " + baseRates.get(currency) + " by " + baseRates.get(baseCurrency.toUpperCase()));
                convertedRates.put(currency, baseRates.get(baseCurrency.toUpperCase()).divide(baseRates.get(currency) , 4, RoundingMode.HALF_UP));
                //convertedRates.put(currency, baseRates.get(currency).divide(baseRates.get(baseCurrency), 4, RoundingMode.HALF_UP));
            }
        }

        return convertedRates;
    }
    private Map<String, BigDecimal> getRates(){
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        InputStream is = null;
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(10000);
            is = connection.getInputStream();
        } catch (IOException ex){
            System.out.println("Connection error: " + ex.getMessage());
            return null;
        }
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(is);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

        } catch (java.lang.Exception e){
            System.out.println("Parse error " + e.getMessage());
            return null;
        }
        NodeList nodes = doc.getElementsByTagName("Valute");
        assert nodes != null;
        assert nodes.getLength() > 0;
        for (int i = 0; i < nodes.getLength(); i++){
            Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                String symbol = element.getElementsByTagName("CharCode").item(0).getTextContent();
                int nominal = Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent());
                BigDecimal value = parseBigDecimalValue(element.getElementsByTagName("Value").item(0).getTextContent());
                if (nominal != 1){
                    value = value.divide(new BigDecimal(nominal));
                }
                rates.put(symbol.toUpperCase(), value);
            }
        }
        return rates;
    }
    protected BigDecimal parseBigDecimalValue(String value){
        BigDecimal parsed = null;
        String pattern = "0.####";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            parsed = (BigDecimal) decimalFormat.parse(value);
        } catch (ParseException e){}
        return parsed;
    }
    @Override
    public List<String> getSupportedCurrencies() {
        List<String> supportedCurrencies = new ArrayList<String>();
        supportedCurrencies.add("AUD");
        supportedCurrencies.add("AZN");
        supportedCurrencies.add("GBP");
        supportedCurrencies.add("AMD");
        supportedCurrencies.add("BYR");
        supportedCurrencies.add("BGN");
        supportedCurrencies.add("BRL");
        supportedCurrencies.add("HUF");
        supportedCurrencies.add("DKK");
        supportedCurrencies.add("USD");
        supportedCurrencies.add("EUR");
        supportedCurrencies.add("INR");
        supportedCurrencies.add("KZT");
        supportedCurrencies.add("CAD");
        supportedCurrencies.add("KGS");
        supportedCurrencies.add("CNY");
        supportedCurrencies.add("MDL");
        supportedCurrencies.add("NOK");
        supportedCurrencies.add("PLN");
        supportedCurrencies.add("RON");
        supportedCurrencies.add("XDR");
        supportedCurrencies.add("SGD");
        supportedCurrencies.add("TJS");
        supportedCurrencies.add("TRY");
        supportedCurrencies.add("TMT");
        supportedCurrencies.add("UZS");
        supportedCurrencies.add("UAH");
        supportedCurrencies.add("CZK");
        supportedCurrencies.add("SEK");
        supportedCurrencies.add("CHF");
        supportedCurrencies.add("ZAR");
        supportedCurrencies.add("KRW");
        supportedCurrencies.add("JPY");
        supportedCurrencies.add("RUR");
        return supportedCurrencies;
    }

    ServiceRegistration registration;

    @Override
    public void start(BundleContext context) throws Exception {
        registration = context.registerService(CurrencyRateModule.class.getName(), this, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registration != null){
            registration.unregister();
        }
    }
}
