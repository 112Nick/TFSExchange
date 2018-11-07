package ru.mail.park.tfsexchange;

import android.support.annotation.NonNull;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mail.park.tfsexchange.api.ExchangeApi;

public class MainPresenter extends Presenter<MainView> {

    private static final int OK = 200;
    private static final String ERR_MSG_BAD_REQUEST = "Bad request";
    private static final String ERR_MSG_NULL = "No internet connection";
    private static final String ERR_MSG_TOO_LONG_VAL = "Value is too long";

    private final ExchangeApi exchangeApi;
    private final CurrencyRepository currencyRepository;

    public MainPresenter(ExchangeApi exchangeApi, CurrencyRepository currencyRepository) {
        this.exchangeApi = exchangeApi;
        this.currencyRepository = currencyRepository;
    }

    public void convert(int value, String fromCurrency, String toCurrency) {
        String pair = fromCurrency + "_" + toCurrency;

        MainView view = getView();
        if (view != null) {
            view.showProgress();
        }

        Callback<Map<String, Double>> callback = new Callback<Map<String, Double>>() {

            @Override
            public void onResponse(@NonNull Call<Map<String, Double>> call, @NonNull Response<Map<String, Double>> response) {
                MainView view = getView();
                if (view == null) {
                    Double currency = response.body().get(pair);
                    currencyRepository.saveCurrency(pair, currency.toString());
                    return;
                }
                if (response.body() != null) {
                    if (response.code() == OK) {
                        view.hideAttentionIcon();
                        if (value == -1) {
                            view.setResultValue(ERR_MSG_TOO_LONG_VAL);
                        } else {
                            Double currency = response.body().get(pair);
                            Double resultNumber = currency * value;
                            view.setResultValue(resultNumber.toString());
                            currencyRepository.saveCurrency(pair, currency.toString());
                        }
                    } else {
                        view.setResultValue(ERR_MSG_BAD_REQUEST);
                    }
                } else {
                    view.setResultValue(ERR_MSG_NULL);
                }
                view.hideProgress();
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Double>> call, @NonNull Throwable t) {
                MainView view = getView();
                if (view == null) {
                    return;
                }
                String currency = currencyRepository.getCurrency(pair);
                if (currency != null) {
                    view.showAttentionIcon();
                    Double cashedVal = Double.valueOf(currency) * value;
                    view.setResultValue(cashedVal.toString());
                } else {
                    view.setResultValue(ERR_MSG_NULL);
                }
                view.hideProgress();
            }
        };
        exchangeApi.getCurrentValue(pair).enqueue(callback);
    }
}
