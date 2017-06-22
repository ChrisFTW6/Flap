package com.chris.flap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.chris.flap.realm.HighScoresModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Realm realm;
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        final EditText edPassword = (EditText) findViewById(R.id.edPassword);
        final EditText edUserName = (EditText) findViewById(R.id.edUserName);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edUserName.getText().toString();
                final String password = edPassword.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success){
                                String name = jsonResponse.getString("name");
                                String score = jsonResponse.getString("score");

                                Number currentIdNum = realm.where(HighScoresModel.class).max("id");
                                int nextId;
                                if(currentIdNum == null) {
                                    nextId = 1;
                                } else {
                                    nextId = currentIdNum.intValue() + 1;}

                                realm.beginTransaction();
                                HighScoresModel highScoresModel = realm.createObject(HighScoresModel.class, nextId);
                                highScoresModel.setScore(Integer.parseInt(score));
                                highScoresModel.setName(name);

                                realm.copyToRealmOrUpdate(highScoresModel);
                                realm.commitTransaction();

                                Intent intent = new Intent(Login.this, UserArea.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", username);
                                intent.putExtra("score", score);

                                Login.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });
    }
}
