package com.example.leaf;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class viewfertilizer extends AppCompatActivity {
    ListView lv;
    String url = "";
    SharedPreferences sh;
    public static ArrayList<String> name, pic, descrription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfertilizer);
        lv = (ListView) findViewById(R.id.reply);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url = sh.getString("url", "") + "and_view_fertilizer";

        try {
            ArrayList<NameValuePair> param = new ArrayList<>();

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = parser.makeHttpRequest(url, "POST", param);
            String status = jsonObject.getString("status");
            if (status.equalsIgnoreCase("1")) {

                JSONArray ja = jsonObject.getJSONArray("data");
                name = new ArrayList<>();
                pic = new ArrayList<>();
                descrription = new ArrayList<>();


                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    name.add(jo.getString("name"));
                    pic.add(jo.getString("pic"));
                    descrription.add(jo.getString("descrription"));


                }
                lv.setAdapter(new custom_view_ferti(getApplicationContext(),name,descrription,pic));
            } else {
                Toast.makeText(this, "No fertilizer available", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
