package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_Application;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_HOME;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.SettingsPreferences;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.ImageViews;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.UI.Particles;

import java.util.List;

/**
 * This project create by SAID MOTYA on 06/17/2020.
 * contact on Facebook : https://web.facebook.com/motya.said
 * contact on Email : zonek.app@hotmail.com or zonek.app@gmail.com
 * it a free code source for member secret gfx
*/

public class Activity_Setting extends AppCompatActivity {


    private ImageViews music_on ,music_off ,sound_on , sound_off;
    private ImageViews back;
//    private TextView version;
    private TextView privacy,moreapps;
    private Particles particles ;
    private HIX_Application HIXApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initializeUI();
        InitializeAds();

    }


    private void InitializeAds(){
        HIXApplication = (HIX_Application)getApplicationContext();
        RelativeLayout view = findViewById(R.id.adView);
        HIXApplication.setBannerAd(view);
    }


    private void initializeUI(){

        HIXApplication =(HIX_Application)getApplicationContext();
        music_on = findViewById(R.id.music_on);
        music_off = findViewById(R.id.music_off);
        sound_on = findViewById(R.id.sound_on);
        sound_off = findViewById(R.id.sound_off);
//        version = findViewById(R.id.version_text);
        privacy = findViewById(R.id.privacy_text);
        moreapps = findViewById(R.id.moreapps);
//        version.setText(""+ BuildConfig.VERSION_NAME);
        particles = findViewById(R.id.particles);
        back = findViewById(R.id.ic_back);
        if (HIX_HOME.useParticles){
            particles.pause();
            particles.setVisibility(View.VISIBLE);
        }

        CheckMusic();
        CheckSound();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPrivacy();
                setAnimation(privacy);
            }
        });
        moreapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoreApps();
                setAnimation(moreapps);
            }
        });

        music_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isVisible(music_on)){
                    setVisible(music_off,true);
                    setVisible(music_on,false);
                    SettingsPreferences.setMusic(getApplicationContext(), false);
                    if (HIX_HOME.useMusic){
                        HIXApplication.StopMuisc();
                    }
                }
            }
        });

        music_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible(music_off)){
                    setVisible(music_off,false);
                    setVisible(music_on,true);
                    SettingsPreferences.setMusic(getApplicationContext(), true);
                    if (HIX_HOME.useMusic){
                        HIXApplication.PlayMusic();
                    }

                }
            }
        });

        sound_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isVisible(sound_on)){
                    setClicksound();
                    setVisible(sound_off,true);
                    setVisible(sound_on,false);
                    SettingsPreferences.setSound(getApplicationContext(), false);
                    if (HIX_HOME.useMusic){
                        HIXApplication.StopSound();
                    }

                }
            }
        });

        sound_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible(sound_off)){
                    setVisible(sound_off,false);
                    setVisible(sound_on,true);
                    SettingsPreferences.setSound(getApplicationContext(), true);
                }
            }
        });


    }

    private void getMoreApps(){

        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(HIX_HOME.Tags.GGDEV+ HIX_HOME.DeveloperName));
        if (isAvailable(mIntent)) {
            startActivity(mIntent);
        } else {
            Toast.makeText(getApplicationContext(), HIX_HOME.Tags.NotAvailableMessage, Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isAvailable(Intent intent) {
        final PackageManager mgr = getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void getPrivacy(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HIX_HOME.PrivacyPolicy));
        if (isAvailable(intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), HIX_HOME.Tags.NotAvailableMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAnimation(final View view){
        view.setScaleX(0.9f);
        view.setScaleY(0.9f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setScaleX(1f);
                view.setScaleY(1f);
            }
        },50);
    }

    public void CheckSound(){

        if (SettingsPreferences.checkSound(getApplicationContext())) {
            try {
                setVisible(sound_on,true);
                setVisible(sound_off,false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }else {
            try {
                setVisible(sound_off,true);
                setVisible(sound_on,false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
    public void CheckMusic(){

        if (SettingsPreferences.checkMusic(getApplicationContext())) {
            try {
                setVisible(music_on,true);
                setVisible(music_off,false);
                if (HIX_HOME.useMusic){
                    HIXApplication.PlayMusic();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }else {
            try {
                setVisible(music_off,true);
                setVisible(music_on,false);
                if (HIX_HOME.useMusic){
                    HIXApplication.StopMuisc();
                }

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private void setVisible(View view, boolean isVisible){
        if (isVisible){
            view.setVisibility(View.VISIBLE);
            return;
        }
        view.setVisibility(View.GONE);
    }

    private boolean isVisible(View view){
        return view.getVisibility()==View.VISIBLE;
    }



    private void setClicksound(){
        if (HIX_HOME.useMusic){
            if (SettingsPreferences.checkSound(getApplicationContext())) {
                HIXApplication.ClickSounds(getApplicationContext(),R.raw.click);
            }
        }

    }
    @Override
    protected void onDestroy() {
        HIXApplication.onDestroyBanner();
        super.onDestroy();
    }

}