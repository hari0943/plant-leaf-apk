package com.example.leaf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_view_tips extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;


    public custom_view_tips(android.content.Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c) {

        this.Context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;



    }

    @Override
    public int getCount() {

        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            gridView=inflator.inflate(R.layout.cust_view_tips, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.sub);
        TextView tv2=(TextView)gridView.findViewById(R.id.con);
      //  TextView tv3=(TextView)gridView.findViewById(R.id.phone);
//        Button b9=(Button)gridView.findViewById(R.id.)



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);


        tv1.setText(b.get(position));
        tv2.setText(c.get(position));

        return gridView;
    }

}


