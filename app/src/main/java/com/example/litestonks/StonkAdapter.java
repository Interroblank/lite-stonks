package com.example.litestonks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;

public class StonkAdapter extends RecyclerView.Adapter<StonkAdapter.ViewHolder> {

    Context                         con;
    ArrayList<StonkTicker_Active>   col;

    public StonkAdapter(Context to_con, ArrayList<StonkTicker_Active> to_col) {
        con = to_con;
        col = to_col;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View v = inflater.inflate(R.layout.stonk_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stonk_sym.setText(col.get(position).get_symbol());
        holder.stonk_nam.setText(col.get(position).get_name());
        String to_stonk_val = "$" + col.get(position).get_price() + " | "
                + col.get(position).get_change() + " | "
                + col.get(position).get_change_per() + "%";
        float change_val = Float.parseFloat(col.get(position).get_change());
        if (change_val >= 0) {
            to_stonk_val = "▲ " + to_stonk_val;
            holder.stonk_val.setTextColor(Color.GREEN);
        }
        else {
            to_stonk_val = "▼ " + to_stonk_val;
            holder.stonk_val.setTextColor(Color.RED);
        }
        holder.stonk_val.setText(to_stonk_val);
    }

    @Override
    public int getItemCount() {
        return col.size();
    } // ?

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stonk_sym;
        TextView stonk_nam;
        TextView stonk_val;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stonk_sym = itemView.findViewById(R.id.stonk_sym);
            stonk_nam = itemView.findViewById(R.id.stonk_nam);
            stonk_val = itemView.findViewById(R.id.stonk_val);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder alert_builder = new AlertDialog.Builder(con);
                    alert_builder.setCancelable(false);
                    alert_builder.setMessage("Stop tracking this stock?");
                    alert_builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int i) {
                            StonkManager.active_ticker_collection.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            d.cancel();
                        }
                    });
                    alert_builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int i) {
                            d.cancel();
                        }
                    });
                    AlertDialog alert = alert_builder.create();
                    alert.show();
                    return true; // ?
                }
            });
        }

    }

}
