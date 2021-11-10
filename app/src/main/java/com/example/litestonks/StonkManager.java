package com.example.litestonks;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;

public class StonkManager {

    public static ArrayList<Searchable> ticker_collection = new ArrayList<>();
    public static ArrayList<StonkTicker_Active> active_ticker_collection = new ArrayList<>();
    public static StonkTicker to_active_stonk;

    public static void reset_ticker_collection() {
        ticker_collection = new ArrayList<>();
    }

}
