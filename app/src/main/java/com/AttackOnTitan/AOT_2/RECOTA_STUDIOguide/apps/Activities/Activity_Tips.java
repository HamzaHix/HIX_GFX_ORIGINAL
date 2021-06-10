package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdsManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.BuildConfig;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Adapters.AdapterTips;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Adapters.AdapterTipsF;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_Application;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Async.SynData;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.Hexing;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.SettingsPreferences;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Models.ModelsTips;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.ImageViews;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.Particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
*/

public class Activity_Tips extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageViews back;
    private AdapterTips adapterTips ;
    private AdapterTipsF adapterTipsF;
    private LinearLayout searching, failed;
    private Button tryAgain;
    private Particles particles ;
    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();
    private AdLoader adLoader;
    private NativeAdsManager mNativeAdsManager;
    private HIX_Application HIXApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initializeUI();

    }

    private void initializeUI(){

        recyclerView = findViewById(R.id.recyclerviewTips);
        back = findViewById(R.id.ic_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        searching = findViewById(R.id.searching);
        failed = findViewById(R.id.failed);
        tryAgain = findViewById(R.id.tryAgain);
        particles = findViewById(R.id.particles);
        HIXApplication = (HIX_Application)getApplicationContext();
        if (HIX_HOME.useParticles){
            particles.pause();
            particles.setVisibility(View.VISIBLE);
        }

        fetchData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                finish();
            }
        });
    }

    private void InitializeAds(){
        HIXApplication = (HIX_Application)getApplicationContext();
        RelativeLayout view = findViewById(R.id.adView);
        HIXApplication.setBannerAd(view);
    }

    private void ShowInterstitial(){
        HIXApplication = (HIX_Application)getApplicationContext();
        HIXApplication.showInterstitial();
    }

    private void fetchData(){
        if (HIX_HOME.ONLINE_OFFLINE){
            if (HIX_HOME.useCRYPTDATA){
                try {
                    if (BuildConfig.DEBUG){
                        Log.d("motya","Load list online + crypted");
                    }
                    setDataOnline(Hexing.getStrings(HIX_HOME.JSONLINK_on));
                } catch (Exception e) {}
            }else {
                if (BuildConfig.DEBUG){
                    Log.d("motya","Load list online not crypted");
                }
                setDataOnline(HIX_HOME.JSONLINK_on);
            }
        }else {
            if (BuildConfig.DEBUG){
                Log.d("motya","Load list offline");
            }
            setDataOffline();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private void setDataOffline(){

        new SynData(getApplicationContext()) {
            @Override
            protected void onDataPreExecute() {
                showLoading(true);
                if (BuildConfig.DEBUG){
                    Log.d("motya","Fetch Offline loading...");
                }
            }

            @Override
            protected void onDataExecute(String result,List<Object> objects,String status,int size) {
                if (status.equalsIgnoreCase(HIX_HOME.Tags.DONE)){
                    if (HIX_HOME.showNativeAdInList){

                        if (HIX_HOME.useONLINE_ADNETWORK){
                            if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                loadNatAdmob(HIXApplication.natAdMob,objects,size);
                            }else if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                loadNatFacebook(HIXApplication.natfacebook,objects,size);
                            }else if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                loadNatMix(HIXApplication.natAdMob, HIXApplication.natfacebook,objects,size);
                            }
                        }else {
                            if (HIX_HOME.useCRYPTDATA){

                                if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                    try {
                                        loadNatAdmob(Hexing.getStrings(HIX_HOME.NativeAds_AdMob),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                    try {
                                        loadNatFacebook(Hexing.getStrings(HIX_HOME.NativeAds_Facebook),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                    try {
                                        loadNatMix(Hexing.getStrings(HIX_HOME.NativeAds_AdMob),Hexing.getStrings(HIX_HOME.NativeAds_Facebook),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else {
                                if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                    loadNatAdmob(HIX_HOME.NativeAds_AdMob,objects,size);
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                    loadNatFacebook(HIX_HOME.NativeAds_Facebook,objects,size);
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                    loadNatMix(HIX_HOME.NativeAds_AdMob, HIX_HOME.NativeAds_Facebook,objects,size);
                                }
                            }
                        }

                    }else {
                        getAdapterData(objects,size);
                        showLoading(false);
                    }
                }else {
                    showFailed();
                }

            }

        }.execute();

    }


    @SuppressLint("StaticFieldLeak")
    private void setDataOnline(String link){

        new SynData(getApplicationContext(),link) {
            @Override
            protected void onDataPreExecute() {
                showLoading(true);
                if (BuildConfig.DEBUG){
                    Log.d("motya","Fetch Online loading...");
                }
            }
            @Override
            protected void onDataExecute(String result,List<Object> objects,String status,int size) {
                if (status.equalsIgnoreCase(HIX_HOME.Tags.DONE)){

                    if (HIX_HOME.showNativeAdInList){

                        if (HIX_HOME.useONLINE_ADNETWORK){
                            if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                loadNatAdmob(HIXApplication.natAdMob,objects,size);
                            }else if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                loadNatFacebook(HIXApplication.natfacebook,objects,size);
                            }else if (HIXApplication.defualtAd.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                loadNatMix(HIXApplication.natAdMob, HIXApplication.natfacebook,objects,size);
                            }
                        }else {
                            if (HIX_HOME.useCRYPTDATA){

                                if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                    try {
                                        loadNatAdmob(Hexing.getStrings(HIX_HOME.NativeAds_AdMob),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                    try {
                                        loadNatFacebook(Hexing.getStrings(HIX_HOME.NativeAds_Facebook),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                    try {
                                        loadNatMix(Hexing.getStrings(HIX_HOME.NativeAds_AdMob),Hexing.getStrings(HIX_HOME.NativeAds_Facebook),objects,size);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else {
                                if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)){
                                    loadNatAdmob(HIX_HOME.NativeAds_AdMob,objects,size);
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)){
                                    loadNatFacebook(HIX_HOME.NativeAds_Facebook,objects,size);
                                }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)){
                                    loadNatMix(HIX_HOME.NativeAds_AdMob, HIX_HOME.NativeAds_Facebook,objects,size);
                                }
                            }
                        }

                    }else {
                        getAdapterData(objects,size);
                        showLoading(false);
                    }

                }else {

                    showFailed();
                }

            }


        }.execute();

    }



    private void loadNatMix(String nativeAdMob,String nativeFacebook,final List<Object> objects,int size){
       try {
           int[] list2 = new int[]{0, 1};
           int random2 = new Random().nextInt(list2.length);
           setNavetMix(random2,nativeAdMob,nativeFacebook,objects,size);
       }catch (Exception e){
           if (BuildConfig.DEBUG){
               Log.d("motya","Somthing wrong about Native Mix : "+e);
           }
       }


    }

    private void setNavetMix(int random,String nativeAdMob,String nativeFacebook,final List<Object> objects,int size){

        switch (random) {
            case 0:
                loadNatAdmob(nativeAdMob,objects,size);
                break;
            case 1:
                loadNatFacebook(nativeFacebook,objects,size);
                break;
        }

    }

    private void loadNatAdmob(final String nativeId,final List<Object> objects,final int size){

        mNativeAds.clear();
        if (HIX_HOME.forceNative){
            getAdapterData(objects,size);
            AdLoader.Builder builder = new AdLoader.Builder(getApplicationContext(), nativeId);
            adLoader = builder.forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            mNativeAds.add(unifiedNativeAd);
                            showLoading(false);
                            if (BuildConfig.DEBUG){
                                Log.d("motya","Native AdMob loaded");
                            }
                            if (!adLoader.isLoading()) {
                                insertAdsInMenuItems(objects);
                            }
                        }
                    }).withAdListener(
                    new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            if (BuildConfig.DEBUG){
                                Log.d("motya","Native AdMob Failed to load");
                            }
                            getAdapterData(objects,size);
                            showLoading(false);
                            if (!adLoader.isLoading()) {
                                insertAdsInMenuItems(objects);
                            }
                        }
                    }).build();


        }else {
            getAdapterData(objects,size);
            showLoading(false);
            AdLoader.Builder builder = new AdLoader.Builder(getApplicationContext(), nativeId);
            adLoader = builder.forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            mNativeAds.add(unifiedNativeAd);
                            if (BuildConfig.DEBUG){
                                Log.d("motya","Native AdMob loaded");
                            }
                            if (!adLoader.isLoading()) {
                                insertAdsInMenuItems(objects);
                            }
                        }
                    }).withAdListener(
                    new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int errorCode) {
                            if (BuildConfig.DEBUG){
                                Log.d("motya","Native AdMob Failed to load");
                            }
                            if (!adLoader.isLoading()) {
                                insertAdsInMenuItems(objects);
                            }
                        }
                    }).build();
        }
        adLoader.loadAds(new AdRequest.Builder().build(),AdFrequency(objects));

    }

    private int AdFrequency(List<Object> objects){
        int size = objects.size();
        try {
            if (size <= 3){
                return 1 ;
            }
            return size / 2;
        }catch (Exception e){
            return 0 ;
        }
    }


    private void loadNatFacebook(String nativeId, final List<Object> objects,final int size){

        if (HIX_HOME.forceNative){
            mNativeAdsManager = new NativeAdsManager(getApplicationContext(), nativeId, 5);
            mNativeAdsManager.loadAds();
            mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
                @Override
                public void onAdsLoaded() {
                    getAdapterDataFb(objects,size,mNativeAdsManager);
                    showLoading(false);
                    if (BuildConfig.DEBUG){
                        Log.d("motya","Native Facebook loaded");
                    }
                }
                @Override
                public void onAdError(AdError adError) {
                    if (BuildConfig.DEBUG){
                        Log.d("motya","Native Facebook Failed to load");
                    }
                    getAdapterData(objects,size);
                    showLoading(false);
                }
            });

        }else {
            mNativeAdsManager = new NativeAdsManager(getApplicationContext(), nativeId, 5);
            mNativeAdsManager.loadAds();
            mNativeAdsManager.setListener(new NativeAdsManager.Listener() {
                @Override
                public void onAdsLoaded() {
                    if (BuildConfig.DEBUG){
                        Log.d("motya","Native Facebook loaded");}
                }
                @Override
                public void onAdError(AdError adError) {
                    if (BuildConfig.DEBUG){
                        Log.d("motya","Native Facebook Failed to load");}
                    getAdapterData(objects,size);
                }
            });
            showLoading(false);
            getAdapterDataFb(objects,size,mNativeAdsManager);
        }





    }

    private void getAdapterData(List<Object> objects, final int size){
        adapterTips = new AdapterTips(getApplicationContext(),objects);
        recyclerView.setAdapter(adapterTips);
        adapterTips.setOnClickItems(new AdapterTips.OnClickItems() {
            @Override
            public void onClick(View view, List<Object> objects, int position) {

                ModelsTips modelsTips = (ModelsTips)objects.get(position);
                String jsonPosition = modelsTips.getPosition();

                // new
                String preview = modelsTips.getPreview();
                // -----------------------------------------------

                Intent intent = new Intent(getApplicationContext(), Activity_Content.class);
                intent.putExtra(Activity_Content.ExtraPosition, jsonPosition);
                intent.putExtra(Activity_Content.ExtraSize , size);
                // new
                intent.putExtra(Activity_Content.extraPreview , preview);
                // -----------------------------------------------
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ShowInterstitial();
                setClicksound();


            }
        });
    }

    private void getAdapterDataFb(List<Object> objects, final int size,NativeAdsManager mNativeAdsManager){
        adapterTipsF = new AdapterTipsF(getApplicationContext(),objects,mNativeAdsManager);
        recyclerView.setAdapter(adapterTipsF);
        adapterTipsF.setOnClickItems(new AdapterTipsF.OnClickItems() {
            @Override
            public void onClick(View view, List<Object> objects, int position) {

                ModelsTips modelsTips = (ModelsTips)objects.get(position);
                String jsonPosition = modelsTips.getPosition();
                // new
                String preview = modelsTips.getPreview();
                // -----------------------------------------------

                Intent intent = new Intent(getApplicationContext(), Activity_Content.class);
                intent.putExtra(Activity_Content.ExtraPosition, jsonPosition);
                intent.putExtra(Activity_Content.ExtraSize , size);
                // new
                intent.putExtra(Activity_Content.extraPreview , preview);
                // -----------------------------------------------
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                ShowInterstitial();
                setClicksound();


            }
        });
    }

    private void showLoading(boolean isLoading){
        if (isLoading){
            setVisible(recyclerView, false);
            setVisible(searching, true);
            setVisible(failed, false);
            return;
        }
        setVisible(recyclerView, true);
        setVisible(searching, false);
        setVisible(failed, false);
    }

    private void showFailed(){
        setVisible(recyclerView, false);
        setVisible(searching, false);
        setVisible(failed ,true);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                fetchData();
            }
        });

    }

    private void setVisible(View view, boolean isVisible){
        if (isVisible){
            view.setVisibility(View.VISIBLE);
            return;
        }
        view.setVisibility(View.GONE);
    }
    private void setClicksound(){
        if (HIX_HOME.useMusic){
            if (SettingsPreferences.checkSound(getApplicationContext())) {
                HIXApplication.ClickSounds(getApplicationContext(),R.raw.click);
            }
        }

    }

    private void insertAdsInMenuItems(List<Object> fetchList) {
        if (mNativeAds.size() <= 0) {
            return;
        }

        try {
            int offset = (fetchList.size() / mNativeAds.size()) + 1;
            int index = 1;
            for (UnifiedNativeAd ad : mNativeAds) {
                fetchList.add(index, ad);
                index = index + offset;
            }
            adapterTips.notifyDataSetChanged();

        }catch (Exception e){
            if (BuildConfig.DEBUG){
                Log.d("motya","Native not replace correct : "+e);
            }
        }


    }


    @Override
    protected void onDestroy() {
        HIXApplication.onDestroyBanner();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        InitializeAds();
        super.onResume();
    }
}