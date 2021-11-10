package com.example.litestonks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONManager_Refresh extends AsyncTask<Void, Void, Void> {

    private static HttpURLConnection ticker_conn;

    public static void ticker_refresh() {
        try { // TODO - This should be nested the other way around.
            for (int i = 0; i < StonkManager.active_ticker_collection.size(); i++) {
                String ticker_JSON_raw = "";
                StonkTicker_Active curr_ticker = StonkManager.active_ticker_collection.get(i);
                URL ticker_URL = new URL("https://cloud.iexapis.com/stable/stock/" +
                        curr_ticker.get_symbol() + "/quote?token=" +
                        "pk_388bb77317cf48f1b22ecd9eb3135103");
                ticker_conn = (HttpURLConnection) ticker_URL.openConnection();
                InputStream ticker_stream = ticker_conn.getInputStream();
                BufferedReader ticker_buffer = new BufferedReader(new InputStreamReader(ticker_stream));
                String ticker_JSON_curr_line = "";
                while (ticker_JSON_curr_line != null) {
                    ticker_JSON_curr_line = ticker_buffer.readLine();
                    ticker_JSON_raw += ticker_JSON_curr_line;
                } JSONObject ticker_JSON_obj = new JSONObject(ticker_JSON_raw);
                curr_ticker.set_price(ticker_JSON_obj.get("latestPrice") + "");
                curr_ticker.set_change(ticker_JSON_obj.get("change") + "");
                curr_ticker.set_change_per(ticker_JSON_obj.get("changePercent") + "");
                // StonkManager.active_ticker_collection.set(i, curr_ticker); // ?
                ticker_conn.disconnect();
            }
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
        ticker_refresh();
        return null; // ?
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        MainActivity.ping_adapter();
    }

}
