package com.chris.flap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserArea extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView etSyncScore = (TextView) findViewById(R.id.textView);
        final TextView etSyncScore3 = (TextView) findViewById(R.id.textView3);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String score = intent.getStringExtra("score");

        String message1 = "Welcome " +name+ " \nYou successfully logged in into our server!";
        String message2 = "Your High Score is: " +score+ " and it is now synchronized with your device!";

        etSyncScore.setText(message1);
        etSyncScore3.setText(message2);


    }
}
