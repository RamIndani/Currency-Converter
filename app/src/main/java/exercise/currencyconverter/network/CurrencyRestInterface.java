package exercise.currencyconverter.network;

import exercise.currencyconverter.model.Currency;
import exercise.currencyconverter.network.constants.RestConstant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by iRamnivas on 9/13/2016.
 */
public interface CurrencyRestInterface {
    @GET(RestConstant.BASE)
    Call<Currency> requestExchangeRate(@Query(RestConstant.BASE_QUERY_PARAM) String base);
}
