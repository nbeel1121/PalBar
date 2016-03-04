package com.example.nabeelkhalaf.finaltest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddSponsor extends ActionBarActivity {
Button addS;
EditText userName ,password,email,comapny , phoneNumber ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sponsor);
        addS = (Button)findViewById(R.id.addSponsorB);
        userName = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        comapny = (EditText)findViewById(R.id.company);

        addS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject json = new JSONObject();
                final String userNameT  = userName.getText().toString();
                final String passwordT = password.getText().toString() ;
                final String emailT =  email.getText().toString() ;
                final String phoneNumberT = phoneNumber.getText().toString() ;
                final String companyT = comapny.getText().toString() ;

                try {
                    json.put("username", userNameT);
                    json.put("password", passwordT);
                    json.put("email", email);
                    json.put("phoneNumber",phoneNumberT);
                    json.put("compay",companyT);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                HttpUtil.postRequest("http://192.168.1.116/phpP/addSponsor.php", json, new HttpUtil.HttpRequestCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(response);
                            String message= jsonObj.getString("Message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
    }

}
