package com.example.nabeelkhalaf.finaltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PalBarAdmin extends ActionBarActivity {
Button addS  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pal_bar_admin);
        addS = (Button)findViewById(R.id.addSponsorB);
        addS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),AddSponsor.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        EditText welcomeMessage =  (EditText)findViewById(R.id.M);
        welcomeMessage.setText("Welcome Admin");
    }
}
