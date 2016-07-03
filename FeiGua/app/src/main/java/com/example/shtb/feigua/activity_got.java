package com.example.shtb.feigua;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class activity_got extends Activity {
    List<got_class> classList = new ArrayList<got_class>();
    String s_lesson,s_price,s_time,s_position,s_reward,s_teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_got);
        initclass();
        got_class_Adapter adapter = new got_class_Adapter(activity_got.this,R.layout.got_class, classList);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);
    }

    private void initclass()
    {
            global_data.my_net.got=false;
            new Thread(new Runnable() {
                public void run() {
                    global_data.my_net.sendMsg("f00000",global_data.name);
                }
            }).start();
            int wti=0;
            Toast.makeText(activity_got.this, "。。查询中。。", Toast.LENGTH_SHORT).show();
            while(!global_data.my_net.got&&wti<10)
            {
                wti++;
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(global_data.my_net.message.equals("lesson\t"))
            {
                Toast.makeText(activity_got.this,"暂无可用课程！",Toast.LENGTH_SHORT).show();
            }
            else if(global_data.my_net.message.equals(""))
            {
                Toast.makeText(activity_got.this,"查询失败,请检查网络后重试！",Toast.LENGTH_SHORT).show();
                global_data.my_net.good_net=false;
            }
            else
            {
                String[] ss = global_data.my_net.message.split("\t");
                int len=ss.length;
                int wait_read=len;
                while(wait_read>=5)
                {
                    s_lesson=ss[len-wait_read+1];
                    s_position=ss[len-wait_read+2];
                    s_time=ss[len-wait_read+3];
                    s_reward=ss[len-wait_read+4];
                    s_teacher=ss[len-wait_read+5];
                    wait_read-=5;
                    got_class cla=new got_class(s_lesson,s_position,s_reward,s_time,s_teacher);
                    classList.add(cla);
                }
            }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_got, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
