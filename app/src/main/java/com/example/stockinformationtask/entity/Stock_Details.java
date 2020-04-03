package com.example.stockinformationtask.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stock_Details {

    @SerializedName("symbols_requested")
    @Expose
    private Integer symbolsRequested;
    @SerializedName("symbols_returned")
    @Expose
    private Integer symbolsReturned;
    @SerializedName("data")
    @Expose
    private List<Stock_Details_Entity> data = null;

    public Integer getSymbolsRequested() {
        return symbolsRequested;
    }

    public void setSymbolsRequested(Integer symbolsRequested) {
        this.symbolsRequested = symbolsRequested;
    }

    public Integer getSymbolsReturned() {
        return symbolsReturned;
    }

    public void setSymbolsReturned(Integer symbolsReturned) {
        this.symbolsReturned = symbolsReturned;
    }

    public List<Stock_Details_Entity> getData() {
        return data;
    }

    public void setData(List<Stock_Details_Entity> data) {
        this.data = data;
    }
}
