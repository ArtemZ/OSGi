package ru.multicabinet.currency.defaultcurrencymodules;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * Date: 1/7/15
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */

public class EcbCurrencyService implements CurrencyRateModule, BundleActivator{
    @Override
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("name", "ecb");
        info.put("desc", "European Central Bank");
        info.put("version", "0.1");
        info.put("currency", "EUR");
        return info;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ModuleOptionTemplate> getRequiredModuleOptions() {
        List<ModuleOptionTemplate> moduleOptionTemplates = new ArrayList<ModuleOptionTemplate>();
        return moduleOptionTemplates;
    }
    protected BigDecimal parseBigDecimalValue(String value){
        BigDecimal parsed = null;
        String pattern = "0.####";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            parsed = (BigDecimal) decimalFormat.parse(value);
        } catch (ParseException e){}
        return parsed;
    }
    @Override
    public Map<String, BigDecimal> getCurrenciesRates(String baseCurrency, CurrencyRateModuleData currencyRateModuleData) {
        if (!getSupportedCurrencies().contains(baseCurrency.toUpperCase())){
            return null;
        }
        Map<String, BigDecimal> baseRates = getRates();
        Map<String, BigDecimal> convertedRates = new HashMap<String, BigDecimal>();
        baseRates.put((String)getInfo().get("currency"), new BigDecimal(1));
        assert getInfo().get("currency") != null;
        assert baseCurrency != null;
        if (getInfo().get("currency").equals(baseCurrency.toUpperCase())){
            convertedRates = baseRates;

        } else {
            for (String currency : baseRates.keySet()){
                //System.out.println("Dividing " + baseRates.get(currency) + " by " + baseRates.get(baseCurrency.toUpperCase()));
                convertedRates.put(currency, baseRates.get(currency).divide(baseRates.get(baseCurrency.toUpperCase()) , 4, RoundingMode.HALF_UP));
            }
        }

        return convertedRates;
    }
    private Map<String, BigDecimal> getRates(){
        Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
        InputStream is = null;
        try {
            URL url = new URL("http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
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

        } catch (Exception e){
            System.out.println("Parse error " + e.getMessage());
            return null;
        }
        NodeList cubeNode = doc.getElementsByTagName("Cube");
        assert cubeNode != null;

        for (int i = 0; i < cubeNode.getLength(); i++){
            Node node = cubeNode.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && ((Element) node).hasAttribute("currency")){
                Element element = (Element) node;
                String symbol = element.getAttribute("currency");
                BigDecimal value = parseBigDecimalValue(element.getAttribute("rate"));
                rates.put(symbol.toUpperCase(), value);
            }
        }
        return rates;
    }
    @Override
    public List<String> getSupportedCurrencies() {
        List<String> supportedCurrencies = new ArrayList<String>();
        supportedCurrencies.add("AUD");
        supportedCurrencies.add("GBP");
        supportedCurrencies.add("BGN");
        supportedCurrencies.add("BRL");
        supportedCurrencies.add("HUF");
        supportedCurrencies.add("DKK");
        supportedCurrencies.add("USD");
        supportedCurrencies.add("EUR");
        supportedCurrencies.add("INR");
        supportedCurrencies.add("KZT");
        supportedCurrencies.add("CAD");
        supportedCurrencies.add("CNY");
        supportedCurrencies.add("MDL");
        supportedCurrencies.add("NOK");
        supportedCurrencies.add("PLN");
        supportedCurrencies.add("RON");
        supportedCurrencies.add("SGD");
        supportedCurrencies.add("TRY");
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
