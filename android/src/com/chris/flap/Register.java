package com.chris.flap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.chris.flap.realm.HighScoresModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.Sort;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Realm realm;
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        String score;
        try{score = String.valueOf(realm.where(HighScoresModel.class).findAllSorted("score", Sort.DESCENDING).first().getScore());}catch (IndexOutOfBoundsException e){score = "0";}

        final EditText edName = (EditText) findViewById(R.id.edName);
        final EditText edPassword = (EditText) findViewById(R.id.edPassword);
        final EditText edUserName = (EditText) findViewById(R.id.edUserName);
        final Button registerBtn = (Button) findViewById(R.id.registerBtn);
        final String finalScore = score;

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edName.getText().toString();
                final String username = edUserName.getText().toString();
                final String password = edPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(Register.this, Login.class);
                                Register.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                RegisterRequest registerRequest = new RegisterRequest(name, username, password, finalScore, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });
    }
}
