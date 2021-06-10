package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.RawRes;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.BuildConfig;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Async.SynAd;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.Hexing;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.SettingsPreferences;
import com.onesignal.OneSignal;

import java.util.Random;

/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
 */
public class HIX_Application extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public static MediaPlayer player;
    public static MediaPlayer mMediaPlayer;

    private InterstitialAd mInterstitialAdMob;
    private AdView mAdViewAdMob;
    private RelativeLayout relativeLayout;

    private com.facebook.ads.InterstitialAd mInterstitialFacebook;
    private com.facebook.ads.AdView mAdViewFacebook;

    public String defualtAd = "";
    public String AdMobID = "";
    public String natAdMob = "";
    public String natfacebook = "";
    private String tag = "secret_gfx";


    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        buildRequestAd();
        player = new MediaPlayer();
        mediaPlayerInitializer();
        if (HIX_HOME.useMusic) {
            CheckMusic();
        }
        if (BuildConfig.DEBUG) {
            getLinkData();
            if (HIX_HOME.ONLINE_OFFLINE) {
                onlineData();
            } else {
                onfflineData();
            }
        }

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(HIX_HOME.OneSignalAppId);
    }

    private void onfflineData() {
        if (HIX_HOME.useCRYPTDATA) {
            if (!HIX_HOME.useONLINE_ADNETWORK) {
                cryptData();
            }
            return;
        }
        warningLog();

    }

    private void onlineData() {
        if (HIX_HOME.useCRYPTDATA) {
            getLinkData();
            return;
        }
        warningLog();


    }

    private void cryptData() {

        if (BuildConfig.DEBUG) {
            try {
                String admobId = Hexing.setStrings(HIX_HOME.AdMob_Id);
                String banner_admob = Hexing.setStrings(HIX_HOME.Banner_AdMob);
                String interstitial_admob = Hexing.setStrings(HIX_HOME.Interstitial_AdMob);
                String native_admob = Hexing.setStrings(HIX_HOME.NativeAds_AdMob);
                String banner_facebook = Hexing.setStrings(HIX_HOME.Banner_AdUnit_Facebook);
                String interstitial_facebook = Hexing.setStrings(HIX_HOME.Interstitial_AdUnit_Facebook);
                String native_facebook = Hexing.setStrings(HIX_HOME.NativeAds_Facebook);

                if (BuildConfig.DEBUG) {
                    Log.d(tag, "*******************AdMob ID crypt***********************");
                    Log.d(tag, "AdMob id is : " + admobId);
                    Log.d(tag, "Banner AdMob crypt is : " + banner_admob);
                    Log.d(tag, "Interstitial AdMob crypt is : " + interstitial_admob);
                    Log.d(tag, "Native AdMob crypt is : " + native_admob);
                    Log.d(tag, "*******************Facebook ID crypt***********************");
                    Log.d(tag, "Banner Facebook crypt is : " + banner_facebook);
                    Log.d(tag, "Interstitial Facebook crypt is : " + interstitial_facebook);
                    Log.d(tag, "Native Facebook crypt is : " + native_facebook);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getLinkData() {
        try {
            String link = Hexing.setStrings(HIX_HOME.JSONLINK_on);
            Log.d(tag, "new Link crypt is : " + link);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void warningLog() {
        if (BuildConfig.DEBUG) {
            Log.d(tag, "**********************************************************");
            Log.d(tag, "****** Attention crypt data not active @@@@@        ******");
            Log.d(tag, "****** Your App it be compiled by reverse engine !! ******");
            Log.d(tag, "****** I recommended to use crypt data              ******");
            Log.d(tag, "****** Contact me On Facebook:                      ******");
            Log.d(tag, "****** https://web.facebook.com/motya.said          ******");
            Log.d(tag, "**********************************************************");

        }
    }

    private void buildRequestAd() {

        if (HIX_HOME.useONLINE_ADNETWORK) {
            try {
                if (HIX_HOME.useCRYPTDATA) {
                    onAdOnline(Hexing.getStrings(HIX_HOME.JSONLINK_on));
                } else {
                    onAdOnline(HIX_HOME.JSONLINK_on);
                }
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "use ads online");
                }

            } catch (Exception e) {
            }
        } else {
            onAdOffline();
            if (BuildConfig.DEBUG) {
                Log.d("motya", "use ads offline");
            }
        }

    }

    private void onAdOffline() {

        if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)) {
            try {

                if (HIX_HOME.useCRYPTDATA) {
                    if (BuildConfig.DEBUG) {
                        Log.d("motya", "Call RequestAdMob Crypt");
                    }
                    RequestAdMobAd(Hexing.getStrings(HIX_HOME.Banner_AdMob), Hexing.getStrings(HIX_HOME.Interstitial_AdMob));

                } else {
                    if (BuildConfig.DEBUG) {
                        Log.d("motya", "Call RequestAdMob Not Crypt");
                    }
                    RequestAdMobAd(HIX_HOME.Banner_AdMob, HIX_HOME.Interstitial_AdMob);
                }

            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "onAdOnline AdMob Build Failed causes :" + e);
                }
            }
        } else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)) {
            try {

                if (HIX_HOME.useCRYPTDATA) {
                    if (BuildConfig.DEBUG) {
                        Log.d("motya", "Call RequestFacebook Crypt");
                    }
                    RequestFacebookAd(Hexing.getStrings(HIX_HOME.Banner_AdUnit_Facebook), Hexing.getStrings(HIX_HOME.Interstitial_AdUnit_Facebook));
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.d("motya", "Call RequestFacebook Not Crypt");
                    }
                    RequestFacebookAd(HIX_HOME.Banner_AdUnit_Facebook, HIX_HOME.Interstitial_AdUnit_Facebook);
                }

            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "onAdOnline Facebook Build Failed causes :" + e);
                }
            }
        }else if (HIX_HOME.NetworkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)) {
            try {

                if (HIX_HOME.useCRYPTDATA) {
                    RequestAdMobAd(Hexing.getStrings(HIX_HOME.Banner_AdMob), Hexing.getStrings(HIX_HOME.Interstitial_AdMob));
                    RequestFacebookAd(Hexing.getStrings(HIX_HOME.Banner_AdUnit_Facebook), Hexing.getStrings(HIX_HOME.Interstitial_AdUnit_Facebook));
                } else {

                    RequestAdMobAd(HIX_HOME.Banner_AdMob, HIX_HOME.Interstitial_AdMob);
                    RequestFacebookAd(HIX_HOME.Banner_AdUnit_Facebook, HIX_HOME.Interstitial_AdUnit_Facebook);
                }

            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "onAdOnline Facebook Build Failed causes :" + e);
                }
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void onAdOnline(String link) {
        new SynAd(getApplicationContext(), link) {
            @Override
            protected void onDataPreExecute() {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "onAdOnline loading...");
                }
            }

            @Override
            protected void onDataExecute(String result, String status) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "onAdOnline status is " + status);
                }
            }

            @Override
            protected void onAdResult(String networkDefault, String adMobID, String bannerAdMob, String interstitialAdMob, String nativeAdMob, String bannerFacebook, String interstitialFacebook, String nativeFacebook) {
                try {
                    if (networkDefault != null) {
                        defualtAd = networkDefault;
                    }
                    if (adMobID != null) {
                        AdMobID = adMobID;
                    }
                    if (nativeAdMob != null) {
                        natAdMob = nativeAdMob;
                    }
                    if (nativeFacebook != null) {
                        natfacebook = nativeFacebook;
                    }

                    if (networkDefault.equalsIgnoreCase(HIX_HOME.Tags.AdMob)) {
                        try {
                            RequestAdMobAd(bannerAdMob, interstitialAdMob);
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.d("motya", "onAdOnline AdMob Build Failed causes :" + e);
                            }
                        }
                    } else if (networkDefault.equalsIgnoreCase(HIX_HOME.Tags.Facebook)) {
                        try {
                            RequestFacebookAd(bannerFacebook, interstitialFacebook);
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.d("motya", "onAdOnline Facebook Build Failed causes :" + e);
                            }
                        }
                    }else if (networkDefault.equalsIgnoreCase(HIX_HOME.Tags.Mix)) {
                        try {
                            RequestAdMobAd(bannerAdMob, interstitialAdMob);
                            RequestFacebookAd(bannerFacebook, interstitialFacebook);
                        } catch (Exception e) {
                            if (BuildConfig.DEBUG) {
                                Log.d("motya", "onAdOnline Ad Mix Build Failed causes :" + e);
                            }
                        }
                    }
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.d("motya", "Opps somthing happer in your server :" + e);
                        Log.d("motya", "send your error code to the developper" );
                    }
                }

            }
        }.execute();

    }

    public void RequestAdMobAd(String banner, String Interstitial) {
        // Initialize the AdMob Network SDK :
        MobileAds.initialize(this);

        //Load AdMob Interstitial :
        mInterstitialAdMob = new InterstitialAd(this);
        mInterstitialAdMob.setAdUnitId(Interstitial);
        final AdRequest adRequestInterstitial = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAdMob.loadAd(adRequestInterstitial);
        mInterstitialAdMob.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Interstitial Loaded");
                }

            }


            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Interstitial onAdFailedToLoad");
                }


            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAdMob.loadAd(adRequestInterstitial);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Interstitial onAdClosed");
                }

            }
        });


        mAdViewAdMob = new AdView(this);
        mAdViewAdMob.setAdSize(AdSize.SMART_BANNER);
        mAdViewAdMob.setAdUnitId(banner);
        AdRequest adRequestBanner = new AdRequest
                .Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdViewAdMob.loadAd(adRequestBanner);
        mAdViewAdMob.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Banner on Loaded");
                }

                if (relativeLayout != null) {
                    setBannerAd(relativeLayout);
                }

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Failed to load Banner");
                }


            }
        });

    }

    public void RequestFacebookAd(String banner, String Interstitial) {
        // Initialize the Audience Network SDK :
        AudienceNetworkAds.initialize(this);

        //Load Facebook BannerAd :
        mAdViewFacebook = new com.facebook.ads.AdView(this, banner, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mAdViewFacebook.loadAd(mAdViewFacebook.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Banner Facebook on Failed Loaded");
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Banner Facebook on Loaded");
                }
                if (relativeLayout != null) {
                    setBannerAd(relativeLayout);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        }).build());

        //Load Facebook InterstitialAd :
        mInterstitialFacebook = new com.facebook.ads.InterstitialAd(this, Interstitial);
        mInterstitialFacebook.loadAd(mInterstitialFacebook.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Interstitial Facebook on Failed Loaded");
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Interstitial Facebook Loaded");
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        }).build());

    }

    // Show Interstitial After Loading :
    public void showInterstitial() {


        try {

            if (HIX_HOME.useONLINE_ADNETWORK) {
                buildInterstitial(defualtAd);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Show Interstitial Online network using is : " + defualtAd);
                }

            } else {
                buildInterstitial(HIX_HOME.NetworkDefault);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Show Interstitial Offline network using is : " + HIX_HOME.NetworkDefault);
                }
            }
        }catch (Exception e){
            if (BuildConfig.DEBUG) {

                Log.d("motya", "Somthing Wrong About Interstitial recheck your code : " + e);
                Log.d("motya", "send your error code to the developper" );
            }
        }



    }

    // Show Banner in RelativeLayout :
    public void setBannerAd(RelativeLayout bannerView) {

        try {

            if (HIX_HOME.useONLINE_ADNETWORK) {
                buildBanner(defualtAd, bannerView);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Show Banner Online network using is : " + defualtAd);
                }
            } else {
                buildBanner(HIX_HOME.NetworkDefault, bannerView);
                if (BuildConfig.DEBUG) {
                    Log.d("motya", "Show Banner Offline network using is : " + HIX_HOME.NetworkDefault);
                }
            }

        }catch (Exception e){
            if (BuildConfig.DEBUG) {
                Log.d("motya", "Somthing Wrong About Banner recheck your code : " + e);
                Log.d("motya", "send your error code to the developper" );
            }
        }



    }




    private void buildInterstitial(String checking) {

        if (checking.equals(HIX_HOME.Tags.AdMob)) {
            setInterstitialAdMob();
        } else if (checking.equals(HIX_HOME.Tags.Facebook)) {
            setInterstitialFacebook();
        } else if (checking.equals(HIX_HOME.Tags.Mix)) {
            int[] list1 = new int[]{0, 1};
            int random1 = new Random().nextInt(list1.length);
            setInterstitialSwitcher(random1);
        }

    }

    public void onDestroyBanner() {}

    private void setInterstitialSwitcher(int number) {
        switch (number) {
            case 0:
                setInterstitialAdMob();
                break;
            case 1:
                setInterstitialFacebook();
                break;
        }

    }

    private void setInterstitialAdMob() {
        // Show Interstitial AdMob After Loading
        if (mInterstitialAdMob != null && mInterstitialAdMob.isLoaded()) {
            mInterstitialAdMob.show();
        }
    }

    private void setInterstitialFacebook() {
        // Show Interstitial Facebook After Loading
        if (mInterstitialFacebook != null && mInterstitialFacebook.isAdLoaded()) {
            mInterstitialFacebook.show();
        }
    }


    private void buildBanner(String check, RelativeLayout relativeLayout) {

        if (check.equals(HIX_HOME.Tags.AdMob)) {
            setBannerAdMob(relativeLayout);
        } else if (check.equals(HIX_HOME.Tags.Facebook)) {
            setBannerFacebook(relativeLayout);
        } else if (check.equals(HIX_HOME.Tags.Mix)) {

            int[] list2 = new int[]{0, 1};
            int random2 = new Random().nextInt(list2.length);
            setBannerSwitcher(random2, relativeLayout);

        }

    }

    private void setBannerSwitcher(int number2, RelativeLayout relativeLayout) {
        switch (number2) {
            case 0:
                setBannerAdMob(relativeLayout);
                break;
            case 1:
                setBannerFacebook(relativeLayout);
                break;
        }

    }

    private void setBannerAdMob(RelativeLayout relativeLayout) {
        if (mAdViewAdMob == null) {
            return;
        }
        if (mAdViewAdMob.getParent() != null) {
            ((ViewGroup) mAdViewAdMob.getParent()).removeView(mAdViewAdMob);
        }
        relativeLayout.removeAllViews();
        relativeLayout.addView(mAdViewAdMob);
        relativeLayout.invalidate();
    }

    private void setBannerFacebook(RelativeLayout relativeLayout) {
        if (mAdViewFacebook == null) {
            return;
        }
        if (mAdViewFacebook.getParent() != null) {
            ((ViewGroup) mAdViewFacebook.getParent()).removeView(mAdViewFacebook);
        }
        relativeLayout.removeAllViews();
        relativeLayout.addView(mAdViewFacebook);
        relativeLayout.invalidate();

    }

    public static void mediaPlayerInitializer() {
        try {
            player = MediaPlayer.create(getAppContext(), R.raw.music_bg);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setLooping(true);
            player.setVolume(1f, 1f);
        } catch (IllegalStateException e) {
            if (BuildConfig.DEBUG) {
                Log.d("music", "somthing wrong about music " + e);
            }
            e.printStackTrace();
        }
    }

    public static void PlayMusic() {
        try {
            if (SettingsPreferences.checkMusic(mContext) && !player.isPlaying()) {
                player.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.d("music", "somthing wrong about music " + e);
            }
            mediaPlayerInitializer();
            player.start();
        }
    }

    public static void ClickSounds(Context context, @RawRes final int mRaw) {
        mMediaPlayer = MediaPlayer.create(getAppContext(), mRaw);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setVolume(0.2f, 0.2f);
        try {
            mMediaPlayer.prepare();

        } catch (Exception e) {
            e.printStackTrace();
            if (BuildConfig.DEBUG) {
                Log.d("motya", "somthing wrong about music " + e);
            }
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mMediaPlayer = null;
            }
        });
        mMediaPlayer.start();
    }

    public static void StopSound() {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d("music", "somthing wrong about music " + e);
            }
        }

    }

    public static void StopMuisc() {
        try {
            if (player.isPlaying()) {
                player.pause();
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d("music", "somthing wrong about music " + e);
            }
        }


    }

    public void CheckMusic() {
        if (SettingsPreferences.checkMusic(getApplicationContext())) {
            try {
                PlayMusic();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            try {
                StopMuisc();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setContext(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }


}

