package exercise.currencyconverter.model;

import android.support.annotation.NonNull;

import java.security.InvalidKeyException;

/**
 * Created by iRamnivas on 9/13/2016.
 */
public class DataUtil {
    private static final DataUtil currencyDataUtil = new DataUtil();

    private Currency baseCurrency;
    private DataUtil(){}

    public static DataUtil getCurrencyDataUtilInstance(){
        return currencyDataUtil;
    }

    public void setBaseCurrency(@NonNull  final Currency baseCurrency){
        this.baseCurrency = baseCurrency;
    }

    public String getBaseCurrencyName() throws InvalidKeyException {
        if(null != baseCurrency){
            return baseCurrency.getBase();
        }
        throw new InvalidKeyException("Base currency is not set");
    }

    public Rates getBaseCurrencyConversionRates() throws InvalidKeyException {
        if(null != baseCurrency){
            return baseCurrency.getRates();
        }
        throw new InvalidKeyException("Base currency is not set");
    }

    public double convertBaseCurrency(final double quantity, final String conversionCurrency){
        double currentConversionRate = getCurrentConversionRate(conversionCurrency);
        return  currentConversionRate * quantity;
    }

    public double convertConversionCurrency(final double quantity, final String conversionCurrency){
        double currentConversionRate = getCurrentConversionRate(conversionCurrency);
        if(currentConversionRate > 0) {
            return quantity / currentConversionRate;
        }else{
            return 0;
        }
    }

    private double getCurrentConversionRate(final String conversionCurrency){
        double currentConversionRate = 0.0;
        if(null == baseCurrency){
            return currentConversionRate;
        }
        if(conversionCurrency.equals(CurrencyConverterConstant.USD)) {
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getUSD();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.AUD)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getAUD();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.BGN)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getBGN();
            }
        }else if (conversionCurrency.equals(CurrencyConverterConstant.BRL)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getBRL();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.CAD)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getCAD();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.CHF)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getCHF();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.CNY)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getCNY();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.CZK)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getCZK();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.DKK)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getDKK();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.GBP)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getGBP();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.HKD)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getHKD();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.HRK)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getHRK();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.HUF)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getHUF();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.IDR)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getIDR();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.ILS)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getILS();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.INR)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getINR();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.JPY)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getJPY();
            }
        }else if( conversionCurrency.equals(CurrencyConverterConstant.KRW)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getKRW();
            }
        }else if( conversionCurrency.equals(CurrencyConverterConstant.MXN)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getMXN();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.MYR)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getMYR();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.NOK)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getNOK();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.NZD)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getNZD();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.PHP)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getPHP();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.PLN)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getPLN();
            }
        }else if( conversionCurrency.equals(CurrencyConverterConstant.RON)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getRON();
            }
        }else if( conversionCurrency.equals(CurrencyConverterConstant.RUB )){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getRUB();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.SEK )){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getSEK();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.SGD)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getSGD();
            }
        }else if( conversionCurrency.equals(CurrencyConverterConstant.THB)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getTHB();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.TRY)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getTRY();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.ZAR)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getZAR();
            }
        }else if(conversionCurrency.equals(CurrencyConverterConstant.EUR)){
            if(baseCurrency.getBase().equalsIgnoreCase(conversionCurrency)){
                currentConversionRate = 1;
            }else{
                currentConversionRate = baseCurrency.getRates().getEUR();
            }
        }
        return currentConversionRate;
    }
}
