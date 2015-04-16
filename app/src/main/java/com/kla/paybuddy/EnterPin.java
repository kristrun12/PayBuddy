package com.kla.paybuddy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kla.paybuddy.data.CardTransaction;


public class EnterPin extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
    }

    public  void pinEntered(View view)
    {
        String gsonTransaction = getIntent().getStringExtra("transaction");
        CardTransaction transaction = new Gson().fromJson(gsonTransaction,CardTransaction.class);


        EditText posPin = (EditText) findViewById(R.id.editPin);

        transaction.setPosPin(posPin.getText().toString());

        Intent intent = new Intent(this, StartTransaction.class);
        intent.putExtra("transaction",new Gson().toJson(transaction));
        startActivity(intent);
        finish();
    }
}
