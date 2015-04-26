package com.kla.paybuddy.service;
import android.os.AsyncTask;
import com.kla.paybuddy.data.TransactionResult;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessControlContext;

/**
 * Created by kla on 31.3.2015.
 */
public class ForwardToken extends AsyncTask <String, Void, TransactionResult> {

    final static String ForwardTokenTAG = "ForwardTokenTag";
    private Exception exception;
    final private AccessControlContext appContext;
    private AsyncTaskCompleteListener<TransactionResult> listener;


    public ForwardToken(AccessControlContext appContext, AsyncTaskCompleteListener<TransactionResult> listener) {

        this.appContext = appContext;
        this.listener = listener;
    }

    @Override
    protected TransactionResult doInBackground(String... params) {

        try {
            // params comes from the execute() call: params[0] is the url.
            return postUrl(params[0], params[1]);
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(TransactionResult result) {

        if(listener !=  null)
        {
            listener.onTaskComplete(result);
        }
        //textView.setText(result);
        /*int rCode = result.optInt("responseCode");

        if (rCode == 200) {
            try {
                System.out.println(result.toString());
               // Gson gson = new Gson();
                //Token token  = gson.fromJson(result.toString(), Token.class);
                Log.d("response",result.toString());


                //Repository.setToken(this.appContext, token);

            } catch (Exception e) {

            }
        } else {
            //Toast.makeText(appContext, "Eitthvað klikkaði", Toast.LENGTH_LONG);
            Log.d("response",result.optString("errorCode"));
        }*/

    }

    private TransactionResult postUrl(String serviceURL, String json_accountInfo) throws IOException {
        InputStream is = null;

        // Remake json-string into json object. There has to be a smarter way to do this, but I cant pass a string and json object
        JSONObject msg = new JSONObject();
        TransactionResult ret = new TransactionResult();
        try {
            msg = new JSONObject(json_accountInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(serviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            // Establish connection
            conn.connect();
            // Get ready to write data
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            osw.write(msg.toString());
            osw.flush();
            osw.close();

            //handle the result
            ret.setResultCode(conn.getResponseCode());
            ret.setResultMessage(conn.getResponseMessage());



            if(ret.getResultCode() == 200)
            {
                is = conn.getInputStream();
            }
            else
            {
                is = conn.getErrorStream();
            }
            ret.setResultContent(readInput(is, conn.getContentLength()));
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return ret;
    }

    private String readInput(final InputStream stream, int length)
    {
        try{
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[length];
            reader.read(buffer);
            return new String(buffer);
        }catch(Exception ex)
        {
            return "";
        }
    }


    // Reads an InputStream and converts it to a JSONObject.
  /*  private JSONObject readJSON(InputStream stream, int len) throws IOException {

        String msg = readInput(stream, len);
        JSONObject ret = new JSONObject();
        try {
            ret = new JSONObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }*/
}
