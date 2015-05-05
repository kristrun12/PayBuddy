package com.kla.paybuddy;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kla.paybuddy.data.CardTransaction;
import com.kla.paybuddy.data.TransactionResult;
import com.kla.paybuddy.service.AsyncTaskCompleteListener;
import com.kla.paybuddy.service.CardReader;
import com.kla.paybuddy.service.ForwardToken;

import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;


public class StartTransaction extends ActionBarActivity implements CardReader.AccountCallback, AsyncTaskCompleteListener<TransactionResult> {



    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    private static final String TAG = "StartTransactionAct";

    private CardReader mCardReader;

    private TextView mVendorName;
    private TextView mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_transaction);

        mCardReader = new CardReader(this);

        //
        // Show info in boxes
        CardTransaction transaction = getTransaction();
        mVendorName = (TextView) findViewById(R.id.textVendor);
        mPrice = (TextView) findViewById(R.id.textAmount);

        mVendorName.setText(String.valueOf(transaction.getTheVendor()));
        mPrice.setText(String.valueOf(transaction.getPrice()));


        // Enable Android Beam and register our card reader callback
        enableReaderMode();
    }

    @Override
    public void onTaskComplete(TransactionResult result) {

        Log.d(TAG, "Got result "+result.toString());
        if(result.getResultCode() == 200)
        {
            Toast.makeText(this,"Greiðsla tókst!",Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            Toast.makeText(this,result.getResultContent(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAccountReceived(final String account) {
        Log.d(TAG, "Account was received " + account);

        final AsyncTaskCompleteListener<TransactionResult> listener = this;
        final Activity ctx = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String stringToSend = stringToSend(account);
                Toast.makeText(ctx,stringToSend,Toast.LENGTH_LONG).show();
                ForwardToken fwToken = new ForwardToken(getContext(), listener);
                fwToken.execute("https://kortagleypir.herokuapp.com/transaction", stringToSend);

            }
        });
    }

    @Override
    public void onAccountError(final String msg)
    {
        final Activity ctx = this;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ctx,msg,Toast.LENGTH_LONG).show();
               
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        disableReaderMode();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableReaderMode();

    }

    private void enableReaderMode() {
        Log.i(TAG, "Enabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {

            nfc.enableReaderMode(activity, mCardReader, READER_FLAGS, null);
            Log.i(TAG, "Reader Mode ON");
        }
    }

    private void disableReaderMode() {
        Log.i(TAG, "Disabling reader mode");
        Activity activity = this;
        NfcAdapter nfc = NfcAdapter.getDefaultAdapter(activity);
        if (nfc != null) {
            nfc.disableReaderMode(activity);
            Log.i(TAG, "Reader Mode OFF");
        }
    }

    private CardTransaction getTransaction()
    {
        String gsonTransaction = getIntent().getStringExtra("transaction");
        Log.d(TAG,"inside stringtosen: "+ gsonTransaction);
        return new Gson().fromJson(gsonTransaction,CardTransaction.class);
    }


    private String stringToSend(String account)
    {
        CardTransaction transaction = getTransaction();

        JSONObject outMsg = new JSONObject();

        try {

            JSONObject jObject = new JSONObject(account);
            String tokenitem = jObject.getString("tokenitem");
            String device_id = jObject.getString("device_id");


            outMsg.put("tokenitem",tokenitem);
            outMsg.put("device_id",device_id);
            outMsg.put("price",transaction.getPrice());
            outMsg.put("vendor",transaction.getTheVendor());
            outMsg.put("posPin",transaction.getPosPin());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outMsg.toString();

    }
    public void finishTransaction(View view)
    {
        finish();
    }
}
