package ru.multicabinet.gateways.examplegateway;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import ru.multicabinet.module.PaymentModuleData;
import ru.multicabinet.module.api.PaymentGateway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 11/8/14
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExampleGatewayService implements PaymentGateway, BundleActivator {

    public Map<String, Object> getInfo(){
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("name", "exmaple");
        info.put("fullname", "Exmaple Payment Gateway");
        info.put("version", "1.0");

        List<String> supportedCurrencies = new ArrayList<String>();
        supportedCurrencies.add("EUR");
        supportedCurrencies.add("RUR");
        supportedCurrencies.add("USD");

        info.put("currencies", supportedCurrencies);
        return info;
    }

    public Map<String, Map<String,Object>> getRequiredFormFields(){

        Map<String, Map<String, Object>> requiredFields = new HashMap<String, Map<String, Object>>();

        Map<String, Object> key = new HashMap<String, Object>();
        key.put("type", "text");
        key.put("desc", "Secret key");


        Map<String, Object> purse = new HashMap<String, Object>();
        purse.put("type", "text");
        purse.put("desc", "Payee purse");

        requiredFields.put("key", key);
        requiredFields.put("purse", purse);

        return requiredFields;
    }

    @Override
    public String getFormActionUrl() {
        return "http://ya.ru";
    }

    @Override
    public String getFormMethod() {
        return "GET";
    }

    /*
        Optional
     */
    @Override
    public String getFormButtonImageUrl() {
        return "http://img.yandex.net/i/www/logo.png";
    }

    public Map<String, Map<String, String>> getFormFields(PaymentModuleData data, BigDecimal paymentAmount, String currency){
        Map<String, Map<String, String>> fields = new HashMap<String, Map<String, String>>();
        Map<String, String> key = new HashMap<String, String>();
        key.put("type", "hidden");
        key.put("value", data.getFormFieldValue("key").toString());

        Map<String, String> purse = new HashMap<String, String>();
        purse.put("type", "hidden");
        purse.put("value", data.getFormFieldValue("purse").toString());

        fields.put("key", key);
        fields.put("purse", purse);
        return fields;
    }

    @Override
    public Long validatePayment(Map<String, String> stringStringMap, PaymentModuleData data) {
        return 1l;
    }

    private ServiceRegistration registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(PaymentGateway.class.getName(), this, null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
