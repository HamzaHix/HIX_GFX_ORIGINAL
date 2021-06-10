package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_Application;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.Hexing;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.SettingsPreferences;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.ImageViews;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.Particles;
import com.suddenh4x.ratingdialog.AppRating;
import com.suddenh4x.ratingdialog.preferences.RatingThreshold;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button starts,setting;
    private ImageViews share,rate,moreApps;
    private Particles particles ;
    private HIX_Application HIXApplication;
    private AlertDialog mAlertDialog,exitDialogue ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();

        // for rate the app
        new AppRating.Builder(this)
                .setIconDrawable(getDrawable(R.drawable.ic_splash))
                .setMinimumLaunchTimes(2)
                .setMinimumDays(1)
                .setMinimumLaunchTimesToShowAgain(1)
                .setMinimumDaysToShowAgain(1)
                .setRatingThreshold(RatingThreshold.THREE)
                .setRateLaterButtonTextId(R.string.text_dialogRate_right)
                .setTitleTextId(R.string.text_First_dialogRate_message)
                .setStoreRatingTitleTextId(R.string.text_dialogRate_message1)
                .setStoreRatingMessageTextId(R.string.text_dialogRate_message2)
                .showIfMeetsConditions();

    }

    private void initializeUI(){
        starts = findViewById(R.id.btn_start);
        setting = findViewById(R.id.btn_setting);
        rate = findViewById(R.id.ic_rate);
        moreApps = findViewById(R.id.ic_moreApps);
        share = findViewById(R.id.ic_share);
        particles = findViewById(R.id.particles);
        HIXApplication = (HIX_Application)getApplicationContext();
        if (HIX_HOME.useParticles){
            particles.pause();
            particles.setVisibility(View.VISIBLE);
        }
        setEvent();
    }

    private void setEvent(){

        starts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                startActivity(new Intent(getApplicationContext(), Activity_Tips.class));
                ShowInterstitial();

            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                startActivity(new Intent(getApplicationContext(), Activity_Setting.class));
                ShowInterstitial();

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                getShareApp();
            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                getRateApp();
            }
        });
        moreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicksound();
                getMoreApps();
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

    private void AcceptTermsEU() {

        ConsentInformation consentInformation = ConsentInformation.getInstance(this);
        HIXApplication = (HIX_Application)getApplicationContext();
        String[] publisherIds = new String[0];
        if (HIX_HOME.ONLINE_OFFLINE){
            publisherIds = new String[]{HIXApplication.AdMobID};
        }else {
            if (HIX_HOME.useCRYPTDATA){
                try {
                    publisherIds = new String[]{Hexing.getStrings(HIX_HOME.AdMob_Id)};
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                publisherIds = new String[]{HIX_HOME.AdMob_Id};
            }

        }
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                switch (consentStatus) {
                    case PERSONALIZED:
                        ConsentInformation.getInstance(getApplicationContext()).setConsentStatus(ConsentStatus.PERSONALIZED);
                        InitializeAds();
                        break;
                    case UNKNOWN:
                        if(ConsentInformation.getInstance(getBaseContext()).isRequestLocationInEeaOrUnknown()){
                            buildAlertDialoguEu();
                        }
                        else {
                            InitializeAds();

                        }
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                InitializeAds();
            }
        });


    }

    private void buildAlertDialoguEu() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_eu, null);
        alertDialog.setView(view).setCancelable(false);
        mAlertDialog = alertDialog.create();
        mAlertDialog.show();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button accepts =  view.findViewById(R.id.accept_policy);
        accepts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.cancel();
                ConsentInformation.getInstance(getApplicationContext()).setConsentStatus(ConsentStatus.PERSONALIZED);
                InitializeAds();
            }

        });
        TextView privacy =  view.findViewById(R.id.consent);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrivacy();

            }
        });
    }

    private void setExitDialogue() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rate, null);
        alertDialog.setView(view).setCancelable(false);
        exitDialogue = alertDialog.create();
        exitDialogue.show();
        exitDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button rate =  view.findViewById(R.id.rate_me);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getRateApp();
              exitDialogue.dismiss();
            }

        });
        TextView no =  view.findViewById(R.id.rate_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();

            }
        });
    }



    private void setClicksound(){
        if (SettingsPreferences.checkSound(getApplicationContext())) {
            HIXApplication.ClickSounds(getApplicationContext(),R.raw.click);
        }
    }

    private void getPrivacy(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HIX_HOME.PrivacyPolicy));
        if (isAvailable(intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), HIX_HOME.Tags.NotAvailableMessage, Toast.LENGTH_SHORT).show();
        }
    }
    private void getShareApp(){
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, "This the best guide app , try it now : \n"+ HIX_HOME.Tags.GGAPPS+getPackageName());
            startActivity(Intent.createChooser(intent, "choose one :"));
        } catch (Exception e) {
        }

    }
    private void getRateApp(){
        try {
            Uri uri = Uri.parse(HIX_HOME.Tags.MARKET+ getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(HIX_HOME.Tags.GGAPPS+getPackageName())));
        }
    }

    private void getMoreApps(){

        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(HIX_HOME.Tags.GGDEV+ HIX_HOME.DeveloperName));
        if (isAvailable(mIntent)) {
            startActivity(mIntent);
        } else {
            Toast.makeText(getApplicationContext(), HIX_HOME.Tags.NotAvailableMessage, Toast.LENGTH_SHORT).show();
        }


    }
    private boolean isAvailable(Intent intent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public void onBackPressed() {
        setExitDialogue();
    }


    @Override
    protected void onDestroy() {
        HIXApplication.onDestroyBanner();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        AcceptTermsEU();
        super.onResume();
    }
}