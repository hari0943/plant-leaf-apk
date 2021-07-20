package com.example.leaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewprofile extends AppCompatActivity  {
    EditText e1,e2,e3,e4,e5,e6,e7;
    String url="";
    SharedPreferences sh;

    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewprofile);
        e1 = (EditText) findViewById(R.id.name);
        e2 = (EditText) findViewById(R.id.dob);
        e3 = (EditText) findViewById(R.id.gender);
        e4 = (EditText) findViewById(R.id.place);
        e5 = (EditText) findViewById(R.id.pin);
        e6= (EditText) findViewById(R.id.email);
        e7 = (EditText) findViewById(R.id.phone);



        bt = (Button) findViewById(R.id.button2);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "and_viewprofile";


        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("lid", sh.getString("lid", "")));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();

            jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("1")) {

                e1.setText(jsonObject.getString("name"));
                e2.setText(jsonObject.getString("dob"));
                e3.setText(jsonObject.getString("gender"));
                e4.setText(jsonObject.getString("place"));
                e5.setText(jsonObject.getString("pin"));
                e6.setText(jsonObject.getString("email"));
                e7.setText(jsonObject.getString("phone"));



            } else {
                Toast.makeText(this, "Invalid user..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }}