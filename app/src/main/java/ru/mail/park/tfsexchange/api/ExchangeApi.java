package ru.mail.park.tfsexchange.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExchangeApi {

    String BASE_URL = "https://free.currencyconverterapi.com";

    @GET("/api/v6/convert?compact=ultra")
    Call<Map<String, Double>> getCurrentValue(@Query("q") String pair);
}
