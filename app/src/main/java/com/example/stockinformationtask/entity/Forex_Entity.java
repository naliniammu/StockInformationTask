package com.example.stockinformationtask.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forex_Entity {


    @SerializedName("symbols_returned")
    @Expose
    private Integer symbolsReturned;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("data")
    @Expose
    private Forex_Data data;

    public Integer getSymbolsReturned() {
        return symbolsReturned;
    }

    public void setSymbolsReturned(Integer symbolsReturned) {
        this.symbolsReturned = symbolsReturned;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Forex_Data getData() {
        return data;
    }

    public void setData(Forex_Data data) {
        this.data = data;
    }
}
