package com.example.litestonks;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONManager_Fetch extends AsyncTask<Void, Void, Void> {

    private static HttpURLConnection ticker_conn;

    public static void ticker_fetch() {
        StonkManager.reset_ticker_collection();
        String ticker_JSON_raw = "";
        try {
            URL ticker_URL = new URL("https://api.iextrading.com/1.0/ref-data/symbols");
            ticker_conn = (HttpURLConnection) ticker_URL.openConnection();
            InputStream ticker_stream = ticker_conn.getInputStream();
            BufferedReader ticker_buffer = new BufferedReader(new InputStreamReader(ticker_stream));
            String ticker_JSON_curr_line = "";
            while (ticker_JSON_curr_line != null) {
                ticker_JSON_curr_line = ticker_buffer.readLine();
                ticker_JSON_raw += ticker_JSON_curr_line;
            } JSONArray ticker_JSON_arr = new JSONArray(ticker_JSON_raw);
            for (int i = 0; i < ticker_JSON_arr.length(); i++) {
                JSONObject ticker_JSON_obj = (JSONObject) ticker_JSON_arr.get(i);
                String curr_tick = (String) ticker_JSON_obj.get("symbol");
                String curr_name = (String) ticker_JSON_obj.get("name");
                StonkTicker curr_stonk = new StonkTicker(curr_tick, curr_name);
                StonkManager.ticker_collection.add(curr_stonk);
            } ticker_conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ticker_fetch();
        return null; // ?
    }

    // @Override
    protected void onPostExecute(Long result) {
        // adapter.notifyDataSetChanged();
        // TODO. (?)
    }

}
