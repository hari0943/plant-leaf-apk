package com.example.leaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    EditText et6;
    RadioButton r1,r2;


    Button bt;
    String url="";
    SharedPreferences sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et=(EditText)findViewById(R.id.name);
        et1=(EditText)findViewById(R.id.dob);
        et2=(EditText)findViewById(R.id.place);
        et3=(EditText)findViewById(R.id.pin);
        et4=(EditText)findViewById(R.id.email);
        et5=(EditText)findViewById(R.id.phone);
        et6=(EditText)findViewById(R.id.password);

        r1=(RadioButton) findViewById(R.id.radioButton);
        r2=(RadioButton) findViewById(R.id.radioButton2);


        bt=(Button)findViewById(R.id.button2);
        bt.setOnClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_reg";

    }

    @Override
    public void onClick(View view) {
        String name=et.getText().toString();
        String dob=et1.getText().toString();
        String place=et2.getText().toString();
        String pin=et3.getText().toString();
        String email=et4.getText().toString();
        String phone=et5.getText().toString();
        String password=et6.getText().toString();

        String gender="";
        if(r1.isChecked())
        {
            gender="Male";
        }
        else
        {
            gender="FeMale";
        }



        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("name", name));
            param.add(new BasicNameValuePair("dob", dob));
            param.add(new BasicNameValuePair("place", place));
            param.add(new BasicNameValuePair("pin", pin));
            param.add(new BasicNameValuePair("email", email));
            param.add(new BasicNameValuePair("phone", phone));
            param.add(new BasicNameValuePair("password",password));
            param.add(new BasicNameValuePair("gender",gender));







            param.add(new BasicNameValuePair("lid", sh.getString("lid","")));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("1")) {

                startActivity(new Intent(signup.this, Login.class));

            } else {
                Toast.makeText(this, "error..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }}

    }

