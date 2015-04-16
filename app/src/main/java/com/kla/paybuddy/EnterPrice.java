package com.kla.paybuddy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kla.paybuddy.data.CardTransaction;
import java.util.Random;


public class EnterPrice extends ActionBarActivity {

    final private String [] first = {"AktuTaktu", "Ísbúð Vesturbæjar","Sjoppan", "Bónus video","Snæland video"};
    final private String [] second = {"Hamborgarafabrikkan", "Búllan","Askur", "Saffran","Eldsmiðjan","N-1"};
    final private String [] third = {"Bónus", "Kostur","Krónana", "Fjarðakaup","Nettó", "Vero moda", "Jack and Jones"};
    final private String [] fourth = {"Ikea", "Húsgagnahöllin","Ilva", "Knastás","Örninn"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_price);


    }



    public void priceChanged(View view) {

        EditText price = (EditText) findViewById(R.id.editPrice);

        int enterdPrice = 0;
        try {
             enterdPrice = Integer.valueOf(price.getText().toString());
        }
        catch (Exception e  ){

            Toast.makeText(this,"Invalid price",Toast.LENGTH_LONG).show();
            return;
        }
        CardTransaction transaction = new CardTransaction();



        transaction.setCurrentPrice(enterdPrice);
        Log.d("MAIN", "Price is " + transaction.getCurrentPrice());
        Random random = new Random();

        if(transaction.getCurrentPrice() < 5000)
        {
            int index = random.nextInt(first.length);
            transaction.setTheVendor(first[index]);
        }
        else if (transaction.getCurrentPrice() > 5000 && transaction.getCurrentPrice() < 15000)
        {
            int index = random.nextInt(second.length);
            transaction.setTheVendor(second[index]);        }
        else if (transaction.getCurrentPrice() > 15000 && transaction.getCurrentPrice() < 50000)
        {
            int index = random.nextInt(third.length);
            transaction.setTheVendor(third[index]);        }
        else{
            int index = random.nextInt(fourth.length);
            transaction.setTheVendor(fourth[index]);        }

        Intent intent = null;
        if(transaction.getCurrentPrice() > 5000)
        {
             intent = new Intent(this, EnterPin.class);
        }
        else
        {
             intent = new Intent(this, StartTransaction.class);
        }

        intent.putExtra("transaction",new Gson().toJson(transaction));
        startActivity(intent);
        finish();
    }


}
