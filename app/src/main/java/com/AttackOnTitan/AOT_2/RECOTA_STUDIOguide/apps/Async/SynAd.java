package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Async;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.BuildConfig;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;

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

public abstract class SynAd extends AsyncTask<String,String,String> {

    protected abstract void onDataPreExecute();
    protected abstract void onDataExecute(String result,String status);
    protected abstract void onAdResult(String networkDefault,String adMobID,String bannerAdMob,String interstitialAdMob,
                                       String nativeAdMob,String bannerFacebook,String interstitialFacebook,String nativeFacebook);


    protected ConnectivityManager connectivityManager ;
    protected NetworkInfo activeNetworkInfo;
    protected OutputStreamWriter outputStreamWriter;
    protected BufferedReader bufferedReader;

    protected Context context ;
    protected String urlLink;
    protected String networkDefault;
    protected String adMobID;
    protected String bannerAdMob;
    protected String interstitialAdMob;
    protected String nativeAdMob;
    protected String bannerFacebook;
    protected String interstitialFacebook;
    protected String nativeFacebook;

    protected HttpURLConnection connection;
    protected URL url = null;
    protected File file ;

    protected String path ="motyaData.json";
    protected String status ;

    public SynAd(Context context, String urlLink) {
        this.context = context;
        this.urlLink = urlLink;
    }
    public SynAd(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return buildConnection();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onDataPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        getDataAd(s);
        onAdResult(networkDefault,adMobID,bannerAdMob,interstitialAdMob,
                nativeAdMob,bannerFacebook,interstitialFacebook,nativeFacebook);
        onDataExecute(s,status);
    }


    protected String getDataAd(String result){

        try {
            JSONObject urlObject = new JSONObject(result);
            JSONObject jsObj = urlObject.getJSONObject(HIX_HOME.JsObject);
            JSONArray infoArray = jsObj.getJSONArray(HIX_HOME.JsArrayAds);

            for (int j=0 ; j< infoArray.length() ; j++){

                JSONObject info = infoArray.getJSONObject(j);

                networkDefault = info.getString(HIX_HOME.JsObjectNetworkAds);
                adMobID = info.getString(HIX_HOME.JsObjectAdID);

                bannerAdMob = info.getString(HIX_HOME.JsObjectAdBanner);
                interstitialAdMob = info.getString(HIX_HOME.JsObjectAdInterstitial);
                nativeAdMob = info.getString(HIX_HOME.JsObjectAdNative);

                bannerFacebook = info.getString(HIX_HOME.JsObjectFbBanner);
                interstitialFacebook = info.getString(HIX_HOME.JsObjectFbInterstitial);
                nativeFacebook = info.getString(HIX_HOME.JsObjectFbNative);


            }
            if (BuildConfig.DEBUG){
                Log.d("motya","Data Ad done !");
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
