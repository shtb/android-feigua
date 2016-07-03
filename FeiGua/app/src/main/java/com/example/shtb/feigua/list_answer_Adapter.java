package com.example.shtb.feigua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shtb on 16-1-17.
 */
public class list_answer_Adapter extends ArrayAdapter<list_answer> {
    private int resourceID;
    public list_answer_Adapter(Context con,int res_id,List<list_answer> objs)
    {
        super(con,res_id,objs);
        resourceID=res_id;
    }
    public View getView(int position,View conv,ViewGroup vgp)
    {
        list_answer this_class = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID, null
        );
        TextView tv10=(TextView)view.findViewById(R.id.tituser);
        TextView tv14=(TextView)view.findViewById(R.id.titanswer);
        tv10.setText(this_class.getUser());
        tv14.setText(this_class.getAnswer());

        return view;
    }
}
