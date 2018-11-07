package ru.mail.park.tfsexchange;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mail.park.tfsexchange.api.ExchangeApi;

public class AppComponent {

    private static AppComponent instance = null;

    private final ExchangeApi exchangeApi;
    private final CurrencyRepository currencyRepository;

    public static AppComponent getInstance() {
        return instance;
    }

    private AppComponent(Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        exchangeApi = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(ExchangeApi.BASE_URL)
                .build()
                .create(ExchangeApi.class);

        currencyRepository = new CurrencyRepository(context);
    }

    public MainPresenter provideMainPresenter() {
        return new MainPresenter(exchangeApi, currencyRepository);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new AppComponent(context);
        }
    }
}
