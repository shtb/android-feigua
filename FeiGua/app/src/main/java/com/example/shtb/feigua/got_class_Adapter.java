package com.example.shtb.feigua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shtb on 15-4-9.
 */
public class got_class_Adapter extends ArrayAdapter<got_class> {
    private int resourceID;
    public got_class_Adapter(Context con,int res_id,List<got_class> objs)
    {
        super(con,res_id,objs);
        resourceID=res_id;
    }
    public View getView(int position,View conv,ViewGroup vgp)
    {
        got_class this_class = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID, null
        );
        TextView tv10=(TextView)view.findViewById(R.id.tname);
        TextView tv14=(TextView)view.findViewById(R.id.tposition);
        TextView tv16=(TextView)view.findViewById(R.id.tintr);
        TextView tv18=(TextView)view.findViewById(R.id.ttime);
        TextView tv20=(TextView)view.findViewById(R.id.tteacher);
        tv10.setText(this_class.getLesson());
        tv14.setText(this_class.getAddress());
        tv16.setText(this_class.getIntr());
        tv18.setText(this_class.getTime());
        tv20.setText(this_class.getTeacher());

        return view;
    }
}
