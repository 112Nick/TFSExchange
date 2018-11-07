package ru.mail.park.tfsexchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int MAX_LENGTH = 8;
    private static final int DEFAULT_VALUE = 1;
    private static final String ATTENTION_FLAG = "attention_flag";

    private MainPresenter mainPresenter;

    private ProgressBar progressBar;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private EditText inputValue;
    private Button convertBtn;
    private EditText resultValue;
    private ImageView attentionIcon;

    private boolean isAttentionShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            isAttentionShown = savedInstanceState.getBoolean(ATTENTION_FLAG, false);
        }
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ATTENTION_FLAG, isAttentionShown);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }


    private void init() {
        progressBar = findViewById(R.id.progressBar);
        fromCurrencySpinner = findViewById(R.id.fromSpinner);
        toCurrencySpinner = findViewById(R.id.toSpinner);
        inputValue = findViewById(R.id.valueField);
        convertBtn = findViewById(R.id.convertBtn);
        resultValue = findViewById(R.id.resultValue);
        attentionIcon = findViewById(R.id.attentionIcon);

        resultValue.setEnabled(false);

        if (isAttentionShown) {
            attentionIcon.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<CharSequence> arrayAdapter =
                ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(arrayAdapter);
        toCurrencySpinner.setAdapter(arrayAdapter);

        attentionIcon.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        convertBtn.setOnClickListener(v -> mainPresenter.convert(getConvertedValue(), getFromCurrencyData(), getToCurrencyData()));

        mainPresenter = AppComponent.getInstance().provideMainPresenter();
        mainPresenter.attachView(this);
    }

    public String getFromCurrencyData() {
        return fromCurrencySpinner.getSelectedItem().toString();

    }

    public String getToCurrencyData() {
        return toCurrencySpinner.getSelectedItem().toString();
    }

    public int getConvertedValue() {
        int value;
        if (inputValue.getText().toString().equals("")) {
            value = DEFAULT_VALUE;
        } else {
            if (inputValue.getText().toString().length() > MAX_LENGTH) {
                value = -1;
            } else {
                value = Integer.valueOf(inputValue.getText().toString());
            }
        }
        return value;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAttentionIcon() {
        isAttentionShown = true;
        attentionIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAttentionIcon() {
        isAttentionShown = false;
        attentionIcon.setVisibility(View.GONE);
    }

    @Override
    public void setResultValue(String res) {
        resultValue.setText(res);
    }
}
