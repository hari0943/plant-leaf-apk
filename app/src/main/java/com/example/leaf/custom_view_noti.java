package com.example.leaf;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class custom_view_noti extends BaseAdapter {

    private android.content.Context Context;
    ArrayList<String> a;
    ArrayList<String> b;
    ArrayList<String> c;


    public custom_view_noti(android.content.Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c) {

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
            gridView=inflator.inflate(R.layout.activity_custom_noti, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.sub);
        TextView tv2=(TextView)gridView.findViewById(R.id.date);
        TextView tv3=(TextView)gridView.findViewById(R.id.con);
//        Button b9=(Button)gridView.findViewById(R.id.)



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));

        return gridView;
    }

}


