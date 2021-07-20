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

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    EditText et1;
    String url="";
    SharedPreferences sh;

    Button bt,btsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et=(EditText)findViewById(R.id.username);
        et1=(EditText)findViewById(R.id.password);

        bt=(Button)findViewById(R.id.login);
        btsignup=(Button)findViewById(R.id.signUp);
        bt.setOnClickListener(this);
        btsignup.setOnClickListener(new View.OnClickListener() {
            @java.lang.Override
            public void onClick(View view) {

                Intent ins= new Intent(getApplicationContext(),signup.class);
                startActivity(ins);
            }
        });
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_login";
    }

    @Override
    public void onClick(View view) {


        String username=et.getText().toString();
        String password=et1.getText().toString();

        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("username", username));
            param.add(new BasicNameValuePair("password", password));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("1")) {
                String lid = jsonObject.getString("lid");
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("lid", lid);
                ed.commit();

                    startActivity(new Intent(Login.this, Home.class));

            } else {
                Toast.makeText(this, "Invalid username or password..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
