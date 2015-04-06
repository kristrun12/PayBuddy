package com.kla.paybuddy;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.security.AccessController.getContext;


public class MainActivity extends ActionBarActivity implements CardReader.AccountCallback {

    private static final String TAG = "MainActivity";
    private CardReader mCardReader;

    private TextView accountText;

    // Recommend NfcAdapter flags for reading from other Android devices. Indicates that this
    // activity is interested in NFC-A devices (including other Android devices), and that the
    // system should not check for the presence of NDEF-formatted data (e.g. Android Beam).
    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountText = (TextView) findViewById(R.id.theText);

        mCardReader = new CardReader(this);
        Log.d(TAG, "Creating view");
        // Disable Android Beam and register our card reader callback
        enableReaderMode();
    }

    @Override
    public void onAccountReceived(final String account) {
        Log.d(TAG, "Account was received " + account);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String ts = new SimpleDateFormat("HH:mm:ss.ssss").format(new Date());

                String message = ts + ": " + account;

                //String stringToPost = account;
                String stringToSend = stringToSend(account);


                accountText.setText(stringToSend);
                Toast.makeText(getApplicationContext(), stringToSend, Toast.LENGTH_LONG).show();
                new ForwardToken(getContext()).execute("https://kortagleypir.herokuapp.com/transaction", stringToSend);
                //CardTransaction trans = new CardTransaction(account, new Random().nextInt(10000));


            }
        });
    }

    private String stringToSend(String account)
    {
        CardTransaction trans = new CardTransaction();
        JSONObject outMsg = new JSONObject();
        String ts = new SimpleDateFormat("HH:mm:ss.ssss").format(new Date());

        try {

            JSONObject jObject = new JSONObject(account);
            String appPin = jObject.getString("appPin");
            String tokenitem = jObject.getString("tokenitem");
            String device_id = jObject.getString("device_id");
            outMsg.put("tokenitem",tokenitem);
            outMsg.put("appPin",appPin);
            outMsg.put("device_id",device_id);
            outMsg.put("price",new Random().nextInt(50000)+100);
            outMsg.put("vendor","Bonus");
            outMsg.put("posPin",4567);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outMsg.toString();

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
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
