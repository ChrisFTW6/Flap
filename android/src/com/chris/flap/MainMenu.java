package com.chris.flap;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity{
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainmenu);

        Button btn = (Button) findViewById(R.id.PlayBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AndroidLauncher.class));
            }
        });
//        int i = 0;
//        if (prefs!=null)
//            i=AssetLoader.getHighScore();
//        Log.d("abc", String.valueOf(i));


        Button SettingsBtn = (Button) findViewById(R.id.SettingsBtn);

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, PrefsActivity.class));
            }
        });

        Button HighScoreBtn = (Button) findViewById(R.id.HighScoreBtn);

        HighScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ScoresActivity.class));
            }
        });

        Button ExitBtn = (Button) findViewById(R.id.ExitBtn);
        ExitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });

        Button GoOnlineBtn = (Button) findViewById(R.id.GoOnline);
        GoOnlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Login.class));
            }
        });


        mediaPlayer = MediaPlayer.create(this, R.raw.music);


    }
    @Override
    protected void onResume() {
        super.onResume();


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notification = settings.getBoolean(getString(R.string.pref_notification), false);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (notification){


                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime(),
                        2*60*1000,
                        pendingIntent);
        } else {alarmManager.cancel(pendingIntent);}


        boolean mousikh = settings.getBoolean(getString(R.string.pref_music), true);

        if(mousikh){
            mediaPlayer.setVolume(0,0.3f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();}
        else {
            mediaPlayer.pause();
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
        }
    }
}
