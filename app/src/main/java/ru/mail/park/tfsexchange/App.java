package ru.mail.park.tfsexchange;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent.init(this);
    }
}
