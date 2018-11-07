package ru.mail.park.tfsexchange;

public interface MainView extends View {

    void showProgress();

    void hideProgress();

    void showAttentionIcon();

    void hideAttentionIcon();

    void setResultValue(String res);
}
