package exercise.currencyconverter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Observable;
import java.util.Observer;

import exercise.currencyconverter.model.DataUtil;
import exercise.currencyconverter.network.RestUtil;

public class MainActivity extends AppCompatActivity implements Observer{
    private String TAG = MainActivity.class.getSimpleName();
    private Spinner spnrBaseCurrency, spnrConversionCurrency;
    private EditText etBaseCurrency, etConversionCurrency;
    private double baseCurrency;
    private double convertedCurrency;
    private int baseSpinnerPosition;
    private int convertionSpinnerPosition;
    private DataUtil dataUtil = DataUtil.getCurrencyDataUtilInstance();
    RestUtil currencyRestUtil = RestUtil.getRestUtilInstance();
    private boolean isUserOperated = false;
    private boolean loadNewConversions = false;
    private ProgressDialog progress;

    private boolean isConversionSpinnerTouched;
    private boolean isBaseSpinnerTouched;
    private boolean isListenerSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnrBaseCurrency = (Spinner) findViewById(R.id.spnrBaseCurrency);
        spnrConversionCurrency = (Spinner) findViewById(R.id.spnrConversionCurrency);
        etBaseCurrency = (EditText) findViewById(R.id.etBaseCurrency);
        etConversionCurrency = (EditText) findViewById(R.id.etConversionCurrency);
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.countries_string_array, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrBaseCurrency.setAdapter(currencyAdapter);
        spnrConversionCurrency.setAdapter(currencyAdapter);

        if (null == savedInstanceState) {
            progress = ProgressDialog.show(this, getString(R.string.app_name),
                    getString(R.string.loading_message), true);
            currencyRestUtil.registerForUpdates(this);
            currencyRestUtil.loadCurrentCurrencyRates(getString(R.string.base_url), getString(R.string.base_currency));
        }
    }

    private void setListeners() {
        if(!isListenerSet) {
            isListenerSet = true;
            spnrBaseCurrency.setOnItemSelectedListener(baseSpnrItemSelectedListener);
            spnrConversionCurrency.setOnItemSelectedListener(conversionSpnrItemSelectedListener);
            etBaseCurrency.addTextChangedListener(twBaseConversion);
            etConversionCurrency.addTextChangedListener(twConversionCurrency);
            spnrBaseCurrency.setOnTouchListener(baseSpinnerTouchedListener);
            spnrConversionCurrency.setOnTouchListener(conversionSpinnerTouchedListener);
        }
    }


    private void removeListeners() {
        if(isListenerSet) {
            isListenerSet = false;
            etBaseCurrency.removeTextChangedListener(twBaseConversion);
            etConversionCurrency.removeTextChangedListener(twConversionCurrency);
            spnrBaseCurrency.setOnItemSelectedListener(null);
            spnrConversionCurrency.setOnItemSelectedListener(null);
            spnrBaseCurrency.setOnTouchListener(null);
            spnrConversionCurrency.setOnTouchListener(null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        if (!etBaseCurrency.getText().toString().trim().isEmpty()) {
            saveInstanceState.putDouble("base", Double.valueOf(etBaseCurrency.getText().toString()));
        }
        if (!etConversionCurrency.getText().toString().trim().isEmpty()) {
            saveInstanceState.putDouble("convertedCurrency", Double.valueOf(etConversionCurrency.getText().toString()));
        }
        saveInstanceState.putInt("baseCurrencyPosition", spnrBaseCurrency.getSelectedItemPosition());
        saveInstanceState.putInt("convertedCurrencyPosition", spnrConversionCurrency.getSelectedItemPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //removeListeners();
        if (savedInstanceState.containsKey("base")) {
            baseCurrency = savedInstanceState.getDouble("base");
            etBaseCurrency.setText(String.valueOf(baseCurrency));
        }
        if (savedInstanceState.containsKey("convertedCurrency")) {
            convertedCurrency = savedInstanceState.getDouble("convertedCurrency");
            etConversionCurrency.setText(String.format("%.2f",convertedCurrency));
        }

        baseSpinnerPosition = savedInstanceState.getInt("baseCurrencyPosition");
        convertionSpinnerPosition = savedInstanceState.getInt("convertedCurrencyPosition");
        spnrBaseCurrency.setSelection(baseSpinnerPosition);
        spnrConversionCurrency.setSelection(convertionSpinnerPosition);
        setListeners();
    }


    AdapterView.OnItemSelectedListener baseSpnrItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(isBaseSpinnerTouched) {
                //removeListeners();
                //progress = ProgressDialog.show(getApplicationContext(), getString(R.string.app_name), getString(R.string.loading_message), true);
                currencyRestUtil.loadCurrentCurrencyRates(getString(R.string.base_url), spnrBaseCurrency.getSelectedItem().toString());
                isBaseSpinnerTouched = false;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnTouchListener baseSpinnerTouchedListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            isBaseSpinnerTouched = true;
            return false;
        }
    };


    View.OnTouchListener conversionSpinnerTouchedListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            isConversionSpinnerTouched = true;
            return false;
        }
    };

    AdapterView.OnItemSelectedListener conversionSpnrItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(isConversionSpinnerTouched) {
                updateConversions(spnrConversionCurrency.getSelectedItem().toString());
                isConversionSpinnerTouched = false;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void updateConversions(String currentConversionCurrency) {

        if(!etBaseCurrency.getText().toString().trim().isEmpty()) {
            convertedCurrency = dataUtil.convertConversionCurrency(Double.valueOf(etBaseCurrency.getText().toString().trim()), spnrConversionCurrency.getSelectedItem().toString());
            etConversionCurrency.setText(String.format("%.2f",convertedCurrency));
        }
    }


    TextWatcher twBaseConversion = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if(getCurrentFocus() == etBaseCurrency && !isBaseSpinnerTouched) {
                try {
                    if (!s.toString().trim().isEmpty() ) {
                        baseCurrency = Double.valueOf(s.toString());
                    } else {
                        baseCurrency = 0;
                    }
                } catch (NumberFormatException npf) {
                    Log.e(TAG, "invalid input");
                    //TODO show error message
                    baseCurrency = 0;
                }
                if(baseCurrency>0) {
                    convertedCurrency = dataUtil.convertBaseCurrency(baseCurrency, spnrConversionCurrency.getSelectedItem().toString());
                    etConversionCurrency.setText(String.format("%.2f",convertedCurrency));
                }else{
                    etConversionCurrency.setText("");
                }
            }
        }
    };

    TextWatcher twConversionCurrency = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if(getCurrentFocus() == etConversionCurrency && !isConversionSpinnerTouched) {
                try {
                    if (!s.toString().trim().isEmpty()) {
                        convertedCurrency = Double.valueOf(s.toString());
                    } else {
                        convertedCurrency = 0;
                    }
                } catch (NumberFormatException nfe) {
                    Log.e(TAG, "invalid input");
                    //TODO : show error message
                    convertedCurrency = 0;
                }
                if(convertedCurrency > 0) {
                    baseCurrency = dataUtil.convertConversionCurrency(convertedCurrency, spnrConversionCurrency.getSelectedItem().toString());
                    etBaseCurrency.setText(String.format("%.2f",baseCurrency));
                }else {
                    etBaseCurrency.setText("");
                }
            }
        }
    };

    @Override
    public void update(Observable observable, Object data) {
        String result = (String) data;
        if(result.equalsIgnoreCase("success")){
            loadNewConversions=false;
            if(null != progress ) {
                progress.dismiss();
                setListeners();
                isUserOperated = false;
                updateConversions(spnrConversionCurrency.getSelectedItem().toString());
            }
        }else{
            showErrorMessage(getString(R.string.error_loading_conversions));
        }
    }

    private void showErrorMessage(String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(errorMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }
}