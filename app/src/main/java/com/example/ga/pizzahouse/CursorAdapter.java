package com.example.ga.pizzahouse;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CursorAdapter extends android.widget.CursorAdapter {

    private Context context;

    public CursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.orders_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView orderTv =  view.findViewById(R.id.orderSummary);
        String order = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY));

        String date = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE));

        String time = cursor.getString(cursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME));
        String orderSummary = order +"\n"+ "Date : " + date +"Time : " + time;
        orderTv.setText(orderSummary);


    }

}