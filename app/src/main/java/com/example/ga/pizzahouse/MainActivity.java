package com.example.ga.pizzahouse;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Pizza> modelArrayList;
    private RecyclerViewAdapter adapter;
    private Toolbar mToolbar ;
    private Button btnnext;
    private  String[] pizzaList = new String[]{"Pepperoni Pizza ", "Chicken Ranch Pizza",
            "Margherita Pizza ", "Chicken BBQ Pizza ","Garden Special Pizza "};
    private  double[] priceList = new double[]{150,140,99.99,130,120};
    private  String[] dList = new String[]{"Pepperoni and mozzarella cheese",
            "Grilled chicken, spicy jalapeno slices, tomatoes, mushroom and ranch sauce",
            "Mozzarella cheese and pizza sauce",
            "Grilled chicken, onion, mushroom and barbeque sauce",
            "Tomatoes, mushroom, green pepper, onion and black olives"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);}
/*        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);}*/

        final TextView emptyState = findViewById(R.id.empty_State);
        btnnext =  findViewById(R.id.next);
        emptyState.setVisibility(View.GONE);

        if (isNetworkOnline()){
            refresh();

        }else {
            emptyState.setVisibility(View.VISIBLE);
            btnnext.setVisibility(View.GONE);
            emptyState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkOnline()) {
                        emptyState.setVisibility(View.GONE);
                        btnnext.setVisibility(View.VISIBLE);
                        refresh();
                    }
                }
            });
        }





    }
    private ArrayList<Pizza> getModel(boolean isSelect){
        ArrayList<Pizza> list = new ArrayList<>();
        for(int i = 0; i < 5; i++){

            Pizza model = new Pizza();
            model.setSelected(isSelect);
            model.setPizzaName(pizzaList[i]);
            model.setPizzaPrice(priceList[i]);
            model.setPizzaDescription(dList[i]);
            list.add(model);
        }
        return list;
    }
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }
    public void refresh(){
        recyclerView =  findViewById(R.id.recycler);


        modelArrayList = getModel(false);
        adapter = new RecyclerViewAdapter(this,modelArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        AdView mAdView =  findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CheckoutActivity.class);
                startActivity(intent);
            }
        });

    }

}


