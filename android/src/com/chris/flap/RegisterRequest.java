package com.chris.flap;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest  extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://83.212.103.71/register.php";
    private Map<String, String> params;

    public  RegisterRequest(String name, String username, String password, String score, Response.Listener<String> Listener){
        super(Method.POST, REGISTER_REQUEST_URL, Listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("score", score);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
