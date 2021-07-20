package com.example.leaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class doubtsreplay extends AppCompatActivity {
    ListView lv;
    String url="";
    SharedPreferences sh;
    public static ArrayList<String> doubts,date,reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubtsreplay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=(ListView)findViewById(R.id.doubts) ;
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_view_doubt_reply";


        try {

            ArrayList<NameValuePair> param = new ArrayList<>();
            param.add(new BasicNameValuePair("lid", sh.getString("lid","")));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = new JSONObject();
            jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");

            if(status.equalsIgnoreCase("1")){

                JSONArray ja=jsonObject.getJSONArray("data");
                doubts=new ArrayList<>();
                date=new ArrayList<>();
                reply=new ArrayList<>();



                for (int i=0;i<ja.length();i++){
                    JSONObject jo=ja.getJSONObject(i);
                    doubts.add(jo.getString("doubts"));
                    date.add(jo.getString("date"));
                    reply.add(jo.getString("replay"));


                }
                lv.setAdapter(new custom_view_reply(getApplicationContext(),doubts,date,reply));
            } else {
                Toast.makeText(this, "no doubts..........", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(doubtsreplay.this,senddoubts.class));

            }
        });
    }

}
