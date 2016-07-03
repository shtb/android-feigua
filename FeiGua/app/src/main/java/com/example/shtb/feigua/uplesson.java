package com.example.shtb.feigua;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static java.lang.Thread.sleep;

public class uplesson extends Activity {
    EditText lesson, time, position, intr;
    String s_lesson, s_time, s_position, s_intr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplesson);
        Button mon = (Button) findViewById(R.id.ub_up);
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lesson = (EditText) findViewById(R.id.u_name);
                time = (EditText) findViewById(R.id.u_time);
                position = (EditText) findViewById(R.id.u_position);
                intr = (EditText) findViewById(R.id.u_intr);
                String les = lesson.getText().toString();
                if (TextUtils.isEmpty(les)) {
                    Toast.makeText(uplesson.this, "请输入课程名称！", Toast.LENGTH_SHORT).show();
                } else {
                    s_lesson = lesson.getText().toString();
                    s_time = time.getText().toString();
                    s_position = position.getText().toString();
                    s_intr = intr.getText().toString();
                    global_data.my_net.got = false;
                    new Thread(new Runnable() {
                        public void run() {
                            global_data.my_net.sendMsg("g00000" , s_lesson + "\n" + s_position + "\n" + s_time + "\n" + s_intr + "\n" + global_data.name);
                        }
                    }).start();
                    int wti = 0;
                    Toast.makeText(uplesson.this, "。。提交中。。", Toast.LENGTH_SHORT).show();
                    while (!global_data.my_net.got && wti < 10) {
                        wti++;
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (global_data.my_net.message.equals("succeed")) {
                        finish();
                    } else if (global_data.my_net.message.equals("NULL")) {
                        Toast.makeText(uplesson.this, "提交失败！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(uplesson.this, "提交失败,请检查网络后重试！", Toast.LENGTH_SHORT).show();
                        global_data.my_net.good_net = false;
                    }
                }
            }
        });
    }
}
