package com.udacity.stockhawk.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;

/**
 * Created by rkalonji on 01/26/2017.
 */

public class Stock implements Parcelable {

    private String mSymbol;
    private String mPrice;
    private String mAbsoluteChange;
    private String mPercentageChange;
    private String mHistory;

    public String getmSymbol() {
        return mSymbol;
    }

    public void setmSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmAbsoluteChange() {
        return mAbsoluteChange;
    }

    public void setmAbsoluteChange(String mAbsoluteChange) {
        this.mAbsoluteChange = mAbsoluteChange;
    }

    public String getmPercentageChange() {
        return mPercentageChange;
    }

    public void setmPercentageChange(String mPercentageChange) {
        this.mPercentageChange = mPercentageChange;
    }

    public String getmHistory() {
        return mHistory;
    }

    public void setmHistory(String mHistory) {
        this.mHistory = mHistory;
    }

    public Stock(String mSymbol, String mPrice, String mAbsoluteChange, String mPercentageChange,
                  String mHistory) {
        this.mSymbol = mSymbol;
        this.mPrice = mPrice;
        this.mAbsoluteChange = mAbsoluteChange;
        this.mPercentageChange = mPercentageChange;
        this.mHistory = mHistory;
    }



    protected Stock(Parcel in) {
        mSymbol = in.readString();
        mPrice = in.readString();
        mAbsoluteChange = in.readString();
        mPercentageChange = in.readString();
        mHistory = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSymbol);
        dest.writeString(mPrice);
        dest.writeString(mAbsoluteChange);
        dest.writeString(mPercentageChange);
        dest.writeString(mHistory);
    }

    private void readFromParcel(Parcel in) {
        mSymbol = in.readString();
        mPrice = in.readString();
        mAbsoluteChange = in.readString();
        mPercentageChange = in.readString();
        mHistory = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Stock> CREATOR = new Parcelable.Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel in) {
            return new Stock(in);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
}
