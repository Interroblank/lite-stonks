package com.example.litestonks;

public class StonkTicker_Active {

    private String symbol;
    private String name;
    private String price;
    private String change;
    private String change_per;

    public StonkTicker_Active(StonkTicker to_sym_and_nam, String to_price,
                              String to_change, String to_change_per) {
        symbol = to_sym_and_nam.get_symbol();
        name = to_sym_and_nam.get_name();
        price = to_price;
        change = to_change;
        change_per = to_change_per;
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

    public String get_price() {
        return price;
    }

    public void set_price(String price) {
        this.price = price;
    }

    public String get_change() {
        return change;
    }

    public void set_change(String change) {
        this.change = change;
    }

    public String get_change_per() {
        return change_per;
    }

    public void set_change_per(String change_per) {
        this.change_per = change_per;
    }

}
