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
public class TestCbr {

    @Test
    public void testParseBigDecimal(){
        BigDecimal number = new BigDecimal("54.11");
        CbrCurrencyService cbr = new CbrCurrencyService();
        //System.out.println(cbr.parseBigDecimalValue("54,1100"));
        //System.out.println(number.equals(cbr.parseBigDecimalValue("54,1100"))  );
        Assert.assertTrue( number.compareTo(cbr.parseBigDecimalValue("54,1100")) == 0 );
    }
    @Test
    public void testGetRates(){
        CbrCurrencyService cbr = new CbrCurrencyService();
        Map<String, BigDecimal> rates = cbr.getCurrenciesRates("USD", null);
        Assert.assertNotNull(rates);
        for(String key : rates.keySet()){
            System.out.println(key + " : " + rates.get(key));
        }
    }
}
