package com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.R;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.Helper.SettingsPreferences;
import com.AttackOnTitan.AOT_2.RECOTA_STUDIOguide.apps.classes.HIX_Application;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class PlayVedio extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private HIX_Application HIXApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vedio);


        // for stop the music
        SettingsPreferences.setMusic(getApplicationContext(), false);
        HIXApplication.StopMuisc();

        // define our views
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        // here we extras the video_link or videoId
        final String videoId = getIntent().getStringExtra("video_link");

        // for use youtub player

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        youTubePlayerView.isFullScreen();


    }



}