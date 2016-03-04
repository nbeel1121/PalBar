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
EditText userName ,password,email,comapny , phoneNumberl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sponsor);
        addS = (Button)findViewById(R.id.addSponsorB);
        userName = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);
        phoneNumberl = (EditText)findViewById(R.id.phoneNumber);
        comapny = (EditText)findViewById(R.id.company);

        addS.setOnClickListener(new View.OnClickListener() {
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

            }
        });
    }

}
