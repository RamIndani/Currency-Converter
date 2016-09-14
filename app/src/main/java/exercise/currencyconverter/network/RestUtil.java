package exercise.currencyconverter.network;

import java.util.Observable;
import java.util.Observer;

import exercise.currencyconverter.model.Currency;
import exercise.currencyconverter.model.DataUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iRamnivas on 9/13/2016.
 */
public class RestUtil extends Observable {
    private static final RestUtil restUtil = new RestUtil();
    private final String TAG = RestUtil.class.getSimpleName();
    private Observer mainActivity;

    private RestUtil(){
    }

    public static RestUtil getRestUtilInstance(){
        return restUtil;
    }

    public void loadCurrentCurrencyRates(final String baseURL, final String currencyBase){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        CurrencyRestInterface currencyRestInterface = retrofit.create(CurrencyRestInterface.class);
        Call<Currency> call = currencyRestInterface.requestExchangeRate(currencyBase);
        call.enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                DataUtil.getCurrencyDataUtilInstance().setBaseCurrency(response.body());
                mainActivity.update(RestUtil.this, new String("success"));
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                mainActivity.update(RestUtil.this, new String("failure"));
            }
        });
    }

    public void registerForUpdates(Observer mainActivity) {
        this.mainActivity = mainActivity;
    }
}
