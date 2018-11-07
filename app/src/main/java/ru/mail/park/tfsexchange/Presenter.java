package ru.mail.park.tfsexchange;

import android.support.annotation.Nullable;

public class Presenter<T extends View> {

    private T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    @Nullable
    protected T getView() {
        return view;
    }
}
