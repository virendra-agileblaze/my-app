package com.agile.ireality.ui.adapter;

/**
 * Created by sarath on 10/2/16.
 */
public class CountrySpinnerAdapterException extends Exception{

    public CountrySpinnerAdapterException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public CountrySpinnerAdapterException(String detailMessage) {
        super(detailMessage);
    }

    public CountrySpinnerAdapterException() {
    }

    public CountrySpinnerAdapterException(Throwable throwable) {
        super(throwable);
    }
}
