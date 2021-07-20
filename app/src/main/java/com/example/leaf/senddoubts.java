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

public class senddoubts extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    Button bt;
    String url="";
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senddoubts);
        et=(EditText)findViewById(R.id.editText);
        bt=(Button)findViewById(R.id.button2);
        bt.setOnClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_senddoubts";
    }


    @Override
    public void onClick(View view) {
        String doubts=et.getText().toString();
        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("lid", sh.getString("lid","")));
            param.add(new BasicNameValuePair("doubts", doubts));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(this, "Success...", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(senddoubts.this, Home.class));

            } else {
                Toast.makeText(this, "error..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

    }}
}
