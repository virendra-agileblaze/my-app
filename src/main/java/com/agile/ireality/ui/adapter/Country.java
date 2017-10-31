package com.agile.ireality.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.agile.ireality.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarath on 10/2/16.
 */
public class Country {
    private String name;
    private String code;
    private Drawable flag;

    public Country(){/*empty*/}
    public Country(String name, Drawable flag, String code) {
        this.name = name;
        this.flag = flag;
        this.code = code;
    }

    public static List<Country> getCountries(Context context) throws CountrySpinnerAdapterException {
        List<Country> countries = new ArrayList<>();
        String[] countryArray = context.getResources().getStringArray(R.array.country_arrays);
        String[] countryCodeArray = context.getResources().getStringArray(R.array.country_code_arrays);
        Drawable[] flagDrawables = new Drawable[countryCodeArray.length];
        for(int i=0; i<flagDrawables.length;i++){
            try {
                flagDrawables[i] = Drawable.createFromStream(context.getAssets().open("flags/"+countryCodeArray[i]+".png"), null);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    flagDrawables[i] = Drawable.createFromStream(context.getAssets().open("flags/in.png"), null);
                } catch (IOException e1) {
                    e.printStackTrace();
                }
            }
        }
        if(countryArray.length != countryCodeArray.length)
            throw new CountrySpinnerAdapterException("Country name array" +
                    " and country code array must have same length");

        for(int position = 0; position< countryArray.length; position++){
            countries.add(new Country(countryArray[position],
                    flagDrawables[position],
                    countryCodeArray[position]));
        }
        return countries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Drawable getFlag() {
        return flag;
    }

    public void setFlag(Drawable flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
