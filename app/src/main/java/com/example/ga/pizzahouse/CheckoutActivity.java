package com.example.ga.pizzahouse;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {
    private TextView tv,emptyState;
    private double sumPrice;
    private String orderSummary;
    private Button order;
    private Button orderHistory;
    private Button findUs;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        toolbar = findViewById(R.id.checkout_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setDisplayShowTitleEnabled(false);}

            //actionBar.setDisplayShowHomeEnabled(true);

        emptyState= findViewById(R.id.empty_State_co);
        emptyState.setVisibility(View.GONE);
        tv =  findViewById(R.id.tv);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < RecyclerViewAdapter.modelArrayList.size(); i++){
            if(RecyclerViewAdapter.modelArrayList.get(i).isSelected()) {

                stringBuffer.append( " " + RecyclerViewAdapter.modelArrayList.get(i).getPizzaName()+" : "+RecyclerViewAdapter.modelArrayList.get(i).getPizzaPrice()+"\n");
                sumPrice += RecyclerViewAdapter.modelArrayList.get(i).getPizzaPrice();

            }
        }
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        SimpleDateFormat sdfD = new SimpleDateFormat("dd MMMM yyyy");
        final String date = sdfD.format(today);
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");
        final String time = sdft.format(today);
        orderSummary = stringBuffer+"\n"+"  total : "+ sumPrice ;
        if (sumPrice==0.00){
            tv.setVisibility(View.GONE);
        }
        tv.setText(orderSummary+ " " + date + " "+ time);
        order = findViewById(R.id.button_id);
        if (sumPrice==0.00){
            order.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        }
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sumPrice!=0.00) {
                    ContentValues orderValues = new ContentValues();
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY,
                            orderSummary);
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE,
                            date);
                    orderValues.put(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME,
                            time);
                    getContentResolver().insert(
                            PizzaContract.PizzaEntry.CONTENT_URI,
                            orderValues);
                    OrdersWidgetService.startAction(CheckoutActivity.this);
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
                    intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }else {

                    Toast.makeText(getApplicationContext(), "plz order something then order ", Toast.LENGTH_SHORT).show();

                }


            }
        });
        orderHistory = findViewById(R.id.orderAc);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckoutActivity.this,OrdersActivity.class);
                startActivity(intent1);
            }
        });
        findUs = findViewById(R.id.find);

        findUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent1 = new Intent(CheckoutActivity.this,MapsActivity.class);
                startActivity(intent1);
/*                Uri gmmIntentUri = Uri.parse("geo:30.106988,31.367909");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);*/

            }
        });
        //Toast.makeText(this, "  " + sumPrice+" ", Toast.LENGTH_SHORT).show();


    }
/*    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
