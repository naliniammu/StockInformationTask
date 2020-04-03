package com.example.stockinformationtask.roomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class DataBaseSearchEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "symbol")
    private String symbol;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "currency")
    private String currency;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "stock_exchange_long")
    private String stock_exchange_long;

    @ColumnInfo(name = "stock_exchange_short")
    private String stock_exchange_short;

    @ColumnInfo(name = "forex_symbol")
    private String forex_symbol;

    public String getForex_symbol() {
        return forex_symbol;
    }

    public void setForex_symbol(String forex_symbol) {
        this.forex_symbol = forex_symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock_exchange_long() {
        return stock_exchange_long;
    }

    public void setStock_exchange_long(String stock_exchange_long) {
        this.stock_exchange_long = stock_exchange_long;
    }

    public String getStock_exchange_short() {
        return stock_exchange_short;
    }

    public void setStock_exchange_short(String stock_exchange_short) {
        this.stock_exchange_short = stock_exchange_short;
    }
}
