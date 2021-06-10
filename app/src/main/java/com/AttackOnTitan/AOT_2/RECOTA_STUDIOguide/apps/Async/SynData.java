package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.BuildConfig;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Models.ModelsTips;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class SynData extends AsyncTask<String,String,String> {

    protected abstract void onDataPreExecute();
    protected abstract void onDataExecute(String result,List<Object> objects,String status,int size);


    protected ConnectivityManager connectivityManager ;
    protected NetworkInfo activeNetworkInfo;
    protected OutputStreamWriter outputStreamWriter;
    protected BufferedReader bufferedReader;
    protected InputStream inputStream;

    protected Context context ;
    protected String urlLink;
    protected HttpURLConnection connection;
    protected URL url = null;
    protected File file ;

    protected String path ="motyaData.json";
    protected String json;
    protected String status ;
    protected int size;

    private ModelsTips modelsTips ;
    private List<Object> objects = new ArrayList<>();



    public SynData(Context context,String urlLink) {
        this.context = context;
        this.urlLink = urlLink;
    }
    public SynData(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        if (HIX_HOME.ONLINE_OFFLINE){
            return buildConnection();
        }else {
            return getData(getJSONOBJECT());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onDataPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (HIX_HOME.ONLINE_OFFLINE){
            getData(s);
        }
        onDataExecute(s,objects,status,size);
    }


    protected String getData(String result){

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject1 = jsonObject.getJSONObject(HIX_HOME.JsObject);
            JSONArray jsonArray = jsonObject1.getJSONArray(HIX_HOME.JsArrayList);

            size = jsonArray.length();
            for (int j= 0 ; j< jsonArray.length() ; j++){

                JSONObject JSobject = jsonArray.getJSONObject(j);

                String title = JSobject.getString(HIX_HOME.JsTitle);
                String photo = JSobject.getString(HIX_HOME.JsPreview);

                modelsTips = new ModelsTips(photo,title,""+(j+1));
                objects.add(modelsTips);
            }
            if (BuildConfig.DEBUG){
                Log.d("motya","Fetch list done !");
            }
            status = HIX_HOME.Tags.DONE;

        } catch (JSONException e) {
            if (BuildConfig.DEBUG){
                Log.d("motya","JsonException error causes : "+e);
            }
            status = HIX_HOME.Tags.FAILED;
            return HIX_HOME.Tags.FAILED ;
        }
        return HIX_HOME.Tags.DONE;


    }

    protected String getJSONOBJECT() {

        try {
            inputStream = context.getAssets().open(HIX_HOME.JSONDATA_off);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    protected String buildConnection(){

        file = new File(context.getFilesDir().getPath() +"/"+path);
        if (checkConnection()) {
            try {
                url = new URL(urlLink);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    InputStream inputStream = connection.getInputStream();
                    return buffToString(new InputStreamReader(inputStream), true);

                } else {

                    if (file.exists()) {
                        return buffToString(new FileReader(file), false);
                    }

                }
            } catch (IOException e2) {
                return HIX_HOME.Tags.FAILED;
            } finally {
                connection.disconnect();
            }

        }
        else {
            try {
                return buffToString(new FileReader(file), false);
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
        return HIX_HOME.Tags.DONE;
    }

    protected void writeJsonToFile(String data, Context context) {
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(path, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String buffToString(Reader ourReader, boolean save) {
        try {
            bufferedReader = new BufferedReader(ourReader);
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            if (save) {
                if (!result.toString().equals(null)) {
                    writeJsonToFile(result.toString(),context);
                }
            }

            return (result.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    protected boolean checkConnection() {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
            if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()) {

                return true;
            }
        }
        return false;
    }

}
