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

public class Complaint extends AppCompatActivity implements View.OnClickListener {
    EditText e1;
    Button b1;
    String url2="";
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        e1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url2=sh.getString("url","")+"and_compliant";
    }

    @Override
    public void onClick(View view) {
        String comlaint=e1.getText().toString();

        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("complaint", comlaint));
            param.add(new BasicNameValuePair("lid", sh.getString("lid","")));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            jsonObject = parser.makeHttpRequest(url2, "POST", param);
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(this, "success.........", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Complaint.this, Home.class));

            } else {
                Toast.makeText(this, "error..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
