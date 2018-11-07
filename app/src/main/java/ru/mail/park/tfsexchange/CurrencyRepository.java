package ru.mail.park.tfsexchange;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class CurrencyRepository {

    private static final String STORAGE_NAME = "cashed_values";

    private final SharedPreferences sharedPreferences;

    public CurrencyRepository(Context context) {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, MODE_PRIVATE);
    }

    public void saveCurrency(String pair, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(pair, value);
        editor.apply();
    }

    public String getCurrency(String pair) {
        return sharedPreferences.getString(pair, null);
    }
}
