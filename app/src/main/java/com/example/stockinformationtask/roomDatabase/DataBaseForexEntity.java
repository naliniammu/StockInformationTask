package com.example.stockinformationtask.roomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(indices = {@Index(value = {"forex_symbol"}, unique = true)})
public class DataBaseForexEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "forex_symbol")
    private String forex_symbol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForex_symbol() {
        return forex_symbol;
    }

    public void setForex_symbol(String forex_symbol) {
        this.forex_symbol = forex_symbol;
    }
}
