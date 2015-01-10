package ru.multicabinet.currency.defaultcurrencymodules;

import junit.framework.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: artemz
 * Date: 1/5/15
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestEcb {

    @Test
    public void testGetRates(){
        EcbCurrencyService ecb = new EcbCurrencyService();
        Map<String, BigDecimal> rates = ecb.getCurrenciesRates("USD", null);
        Assert.assertNotNull(rates);
        for(String key : rates.keySet()){
            System.out.println(key + " : " + rates.get(key));
        }
    }
}
