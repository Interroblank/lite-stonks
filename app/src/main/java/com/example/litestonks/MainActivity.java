package com.example.litestonks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class MainActivity extends AppCompatActivity {

    static RecyclerView stonk_view;
    static StonkAdapter adapter;
    SwipeRefreshLayout refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONManager_Fetch fetch_process = new JSONManager_Fetch();
        fetch_process.execute();
        stonk_view = findViewById(R.id.stonk_view);
        adapter = new StonkAdapter(this, StonkManager.active_ticker_collection);
        stonk_view.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        stonk_view.setLayoutManager(layout);
        refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONManager_Refresh refresh_process = new JSONManager_Refresh();
                refresh_process.execute();
                refresh_layout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stonk_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.stonk_add:
                new SimpleSearchDialogCompat<>(MainActivity.this, "Search for a stonk", "(ex. GME, AMC, BB)",
                        null, StonkManager.ticker_collection, new SearchResultListener<Searchable>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog, Searchable item, int position) {
                        StonkManager.to_active_stonk = (StonkTicker) item;
                        JSONManager_FetchDeep fetch_deep_process = new JSONManager_FetchDeep();
                        fetch_deep_process.execute();
                        dialog.dismiss();
                    }
                }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void ping_adapter() {
        adapter.notifyDataSetChanged();
    }

}