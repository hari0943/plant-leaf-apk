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

public class replay extends AppCompatActivity {
    ListView lv;
    String url="";
    SharedPreferences sh;
    public static ArrayList<String> com,date,reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=(ListView)findViewById(R.id.reply);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"and_view_reply";

        try {
            ArrayList<NameValuePair> param=new ArrayList<>();
            param.add(new BasicNameValuePair("lid",sh.getString("lid","")));

            JSONParser parser=new JSONParser();
            JSONObject jsonObject=parser.makeHttpRequest(url,"POST",param);
            String status=jsonObject.getString("status");
            if(status.equalsIgnoreCase("1")){

                JSONArray ja=jsonObject.getJSONArray("data");
                com=new ArrayList<>();
                date=new ArrayList<>();
                reply=new ArrayList<>();



                for (int i=0;i<ja.length();i++){
                    JSONObject jo=ja.getJSONObject(i);
                    com.add(jo.getString("complaints"));
                    date.add(jo.getString("date"));
                    reply.add(jo.getString("replay"));


                }
                lv.setAdapter(new custom_view_reply(getApplicationContext(),com,date,reply));
            }
            else{
                Toast.makeText(this, "No reply available", Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(replay.this,Complaint.class));
            }
        });
    }

}
