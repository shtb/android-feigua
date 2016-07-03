package com.example.shtb.feigua;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class title extends Activity {
    List<list_answer> classList = new ArrayList<list_answer>();
    String s_user,s_title,s_id;
    String les;
    EditText search_text,title_text,intr_text;
    Button up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        final ListView listView = (ListView) findViewById(R.id.listView3);
//        initclass();
        final Button search=(Button)findViewById(R.id.search);
        up=(Button)findViewById(R.id.uptitle);
        search_text=(EditText)findViewById(R.id.editText2);
        title_text=(EditText)findViewById(R.id.editText3);
        intr_text=(EditText)findViewById(R.id.editText4);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initclass(search_text.getText().toString());
                list_answer_Adapter adapter = new list_answer_Adapter(title.this, R.layout.list_title, classList);
                listView.setAdapter(adapter);
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global_data.my_net.got = false;
                new Thread(new Runnable() {
                    public void run() {
                        global_data.my_net.sendMsg("h00000" , title_text.getText().toString()+ "\n" + intr_text.getText().toString()+ "\n" + global_data.name);
                    }
                }).start();
                int wti = 0;
                Toast.makeText(title.this, "。。提交中。。", Toast.LENGTH_SHORT).show();
                while (!global_data.my_net.got && wti < 10) {
                    wti++;
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (global_data.my_net.message.length()>5) {
                    Toast.makeText(title.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    global_data.click_title = title_text.getText().toString();
                    title_text.setText("");
                    intr_text.setText("");
                    String[] ss = global_data.my_net.message.split("\t");
                    global_data.click_title_id=ss[1];
                    Intent intent=new Intent(title.this,answer.class);
                    startActivity(intent);
                } else if (global_data.my_net.message.equals("NULL")) {
                    Toast.makeText(title.this, "提交失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(title.this, "提交失败,请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    global_data.my_net.good_net = false;
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                list_answer got = classList.get(position);
                global_data.click_title_id = got.getId();
                global_data.click_title = got.getAnswer();
                Intent intent = new Intent(title.this, answer.class);
                startActivity(intent);
            }
        });
    }

    private void initclass(final String sea)
    {
        classList.clear();
        global_data.my_net.got=false;
        new Thread(new Runnable() {
            public void run() {
                global_data.my_net.sendMsg("k00000",sea);
            }
        }).start();
        int wti=0;
        Toast.makeText(title.this, "。。查询中。。", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(title.this,"未找到相关主题！",Toast.LENGTH_SHORT).show();
        }
        else if(global_data.my_net.message.equals(""))
        {
            Toast.makeText(title.this,"查询失败,请检查网络后重试！",Toast.LENGTH_SHORT).show();
            global_data.my_net.good_net=false;
        }
        else
        {
            String[] ss = global_data.my_net.message.split("\t");
            int len=ss.length;
            int wait_read=len;
            while(wait_read>3)
            {
                s_title=ss[len-wait_read+1];
                s_user=ss[len-wait_read+2];
                s_id=ss[len-wait_read+3];
                wait_read-=3;
                list_answer cla=new list_answer(s_title,s_user,s_id);
                classList.add(cla);
            }
        }
    }
}
