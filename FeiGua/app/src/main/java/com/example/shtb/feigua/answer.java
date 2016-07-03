package com.example.shtb.feigua;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class answer extends Activity {
    List<list_answer> classList = new ArrayList<list_answer>();
    TextView tv;
    EditText ans_text;
    ListView listView;
    String s_user,s_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initclass();
        tv=(TextView)findViewById(R.id.bigtitle);
        tv.setText(global_data.click_title);
        ans_text=(EditText)findViewById(R.id.editText5);
        list_answer_Adapter adapter = new list_answer_Adapter(answer.this,R.layout.list_title, classList);
        listView = (ListView) findViewById(R.id.listView4);
        listView.setAdapter(adapter);

        final Button search=(Button)findViewById(R.id.push);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global_data.my_net.got = false;
                new Thread(new Runnable() {
                    public void run() {
                        global_data.my_net.sendMsg("i00000" , ans_text.getText().toString()+ "\n" + global_data.click_title_id+ "\n" + global_data.name);
                    }
                }).start();
                int wti = 0;
                Toast.makeText(answer.this, "。。提交中。。", Toast.LENGTH_SHORT).show();
                while (!global_data.my_net.got && wti < 10) {
                    wti++;
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (global_data.my_net.message.equals("succeed")) {
                    ans_text.setText("");
                    initclass();
                    list_answer_Adapter adapter = new list_answer_Adapter(answer.this, R.layout.list_title, classList);
                    listView.setAdapter(adapter);
                } else if (global_data.my_net.message.equals("NULL")) {
                    Toast.makeText(answer.this, "提交失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(answer.this, "提交失败,请检查网络后重试！", Toast.LENGTH_SHORT).show();
                    global_data.my_net.good_net = false;
                }
            }
        });
    }

    private void initclass()
    {
        classList.clear();
        global_data.my_net.got=false;
        new Thread(new Runnable() {
            public void run() {
                global_data.my_net.sendMsg("j00000",global_data.click_title_id);
            }
        }).start();
        int wti=0;
        Toast.makeText(answer.this, "。。查询中。。", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(answer.this,"暂无回复！",Toast.LENGTH_SHORT).show();
        }
        else if(global_data.my_net.message.equals(""))
        {
            Toast.makeText(answer.this,"查询失败,请检查网络后重试！",Toast.LENGTH_SHORT).show();
            global_data.my_net.good_net=false;
        }
        else
        {
            String[] ss = global_data.my_net.message.split("\t");
            int len=ss.length;
            int wait_read=len;
            while(wait_read>=2)
            {
                s_answer=ss[len-wait_read+1];
                s_user=ss[len-wait_read+2];
                wait_read-=2;
                list_answer cla=new list_answer(s_answer,s_user,"");
                classList.add(cla);
            }
        }
    }

}
