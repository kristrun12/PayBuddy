package com.kla.paybuddy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
/*
 * Start new round. open activity to enter the price
 */
    public void startSale(View view)
    {

        Intent intent = new Intent(this, EnterPrice.class);

        startActivity(intent);
    }

}
