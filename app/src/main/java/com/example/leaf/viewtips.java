package com.example.leaf;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewtips extends AppCompatActivity {
    ListView lv;
    String url = "";
    SharedPreferences sh;
    public static ArrayList<String> tips_id,subject, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtips);
        lv=(ListView)findViewById(R.id.reply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "and_view_tips";

        try {
            ArrayList<NameValuePair> param = new ArrayList<>();
//            param.add(new BasicNameValuePair("lid", sh.getString("lid", "")));

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("ok")) {

                JSONArray ja = jsonObject.getJSONArray("result");
                tips_id= new ArrayList<>();
                subject= new ArrayList<>();
                content= new ArrayList<>();


                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    subject.add(jo.getString("subject"));
                    content.add(jo.getString("content"));
                    tips_id.add(jo.getString("tips_id"));



                }
//                ArrayAdapter<String>ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,subject);
//                lv.setAdapter(ad);

                lv.setAdapter(new custom_view_tips(getApplicationContext(),tips_id,subject,content));

            } else {
                Toast.makeText(this, "No tips available", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
