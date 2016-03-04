package com.example.nabeelkhalaf.finaltest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Connect extends ActionBarActivity {
    Button login;
    EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        login = (Button) findViewById(R.id.login);
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final JSONObject json = new JSONObject();
                final String userNameT  = userName.getText().toString();
                final String pass = password.getText().toString() ;
                try {
                    json.put("username", userNameT);
                    json.put("password", pass);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtil.postRequest("http://192.168.1.116/phpP/test.php",
                        json, new HttpUtil.HttpRequestCallBack() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String userNameJson= jsonObj.getString("username");
                                    String passwordJson = jsonObj.getString("password");
                                    String  isAdmin = jsonObj.getString("isAdmin");

                                        if(userNameT.equals(userNameJson) && pass.equals(passwordJson)){
                                            if(isAdmin.equals("0")){
                                                startActivity(new Intent(getBaseContext(),Sponsor.class));
                                            }else{
                                                startActivity(new Intent(getBaseContext(),PalBarAdmin.class));
                                            }

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(String error) {
                                Log.i("info", "faild");
                            }
                        });
            }
        });
    }
}
