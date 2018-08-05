package com.example.ga.pizzahouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class OrdersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter mAdapter;
    private static final int FAVOURITES_LOADER_ID = 1;
    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        toolbar = findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listView = findViewById(R.id.orderLv);
        mAdapter = new CursorAdapter(this, null);

        listView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(FAVOURITES_LOADER_ID, null, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(OrdersActivity.this);
                dialog.setMessage("hi" + position);
                dialog.setPositiveButton(getResources().getString(R.string.Reorder), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Toast.makeText(getApplicationContext(), "Reorder", Toast.LENGTH_SHORT).show();
                        Cursor currentCursor = mAdapter.getCursor();
                        currentCursor.moveToPosition(position);
                        String order = currentCursor.getString(currentCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_SUMMARY));
/*                        String date = currentCursor.getString(currentCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_DATE));
                        String time = currentCursor.getString(currentCursor.getColumnIndexOrThrow(PizzaContract.PizzaEntry.COLUMN_ORDER_TIME));
                        String orderSummary = order + "\n" + "Date : " + date + "Time : " + time;*/

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"recipient@example.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
                        intent.putExtra(Intent.EXTRA_TEXT, order);
                        startActivity(intent);

                    }
                });
                dialog.setNegativeButton(getString(R.string.DeleteOrder), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        int j = position + 1;
                        Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                        getContentResolver().delete(PizzaContract.PizzaEntry.CONTENT_URI,
                                PizzaContract.PizzaEntry._ID + " = " + j, null);
                        OrdersWidgetService.startAction(OrdersActivity.this);
                        finish();
                        startActivity(getIntent());


                    }
                });
                dialog.show();

            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri ordersQueryUri = PizzaContract.PizzaEntry.CONTENT_URI;
        return new CursorLoader(this,
                ordersQueryUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
