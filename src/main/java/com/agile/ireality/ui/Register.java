package com.agile.ireality.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agile.ireality.R;
import com.agile.ireality.log.AppLog;
import com.agile.ireality.ui.adapter.Country;
import com.agile.ireality.ui.adapter.CountrySpinnerAdapter;
import com.agile.ireality.ui.adapter.CountrySpinnerAdapterException;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    private static final String LOGTAG = "Register";
    private Button btnSignUp;
    private TextView txtSignIn;
    private Spinner spinnerGender;
    private AutoCompleteTextView autoCompleteCountry;
    private TextView txtDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();


    }

    private void initialize() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        autoCompleteCountry = (AutoCompleteTextView) findViewById(R.id.spinnerCountry);
        txtDob = (TextView) findViewById(R.id.txtCalendar);

        setupControls();
    }

    private void setupControls() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this, "Not Implemented", Toast.LENGTH_SHORT).show();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin = new Intent(Register.this, Login.class);
                startActivity(gotoLogin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        /*
        Date of birth click
         */
        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        /*
        Gender Spinner
         */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_gender_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        /*
        Country Spinner
         */
        try {
            final CountrySpinnerAdapter adapterCountry = new CountrySpinnerAdapter(this,
                    R.layout.spinner_country_list_item,
                    Country.getCountries(this)
                    );
            autoCompleteCountry.setAdapter(adapterCountry);
            autoCompleteCountry.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    adapterCountry.getFilter().filter(arg0);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                }
                @Override
                public void afterTextChanged(Editable arg0) {

                }
            });
            autoCompleteCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    autoCompleteCountry.setText(adapterCountry.getCountries().get(position).getName());
                    Drawable d = adapterCountry.getCountries().get(position).getFlag();
                    int bottom = autoCompleteCountry.getMeasuredHeight()-32;
                    int right = (int)(bottom *(4/3.0f));
                    d.setBounds(0, 0, right, bottom);
                    autoCompleteCountry.setCompoundDrawables(null, null,d
                            , null);
                }
            });

        } catch (CountrySpinnerAdapterException e) {
            e.printStackTrace();
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String text = String.format(Register.this.getResources().getString(R.string.date_of_birth),
                        dayOfMonth, monthOfYear+1, year);
                txtDob.setText(text);

            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends DialogFragment{

        private final DatePickerDialog.OnDateSetListener mDateSetListener;

        public DatePickerFragment(DatePickerDialog.OnDateSetListener dateSetListener) {
            this.mDateSetListener = dateSetListener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), mDateSetListener, year, month, day);
        }

    }

}
