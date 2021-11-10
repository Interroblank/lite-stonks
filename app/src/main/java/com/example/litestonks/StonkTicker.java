package com.example.litestonks;

import ir.mirrajabi.searchdialog.core.Searchable;

public class StonkTicker implements Searchable {

    private String symbol;
    private String name;

    public StonkTicker(String to_symbol, String to_name) {
        symbol = to_symbol;
        name = to_name;
    }

    @Override
    public String getTitle() {
        return get_symbol();
    }

    public String get_symbol() {
        return symbol;
    }

    public void set_symbol(String symbol) {
        this.symbol = symbol;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name) {
        this.name = name;
    }

}
