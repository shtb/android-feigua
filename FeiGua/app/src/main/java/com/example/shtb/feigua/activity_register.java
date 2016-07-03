package com.example.shtb.feigua;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class activity_register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_register);
        Button entry=(Button)findViewById(R.id.button_become_student);
        entry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText user=(EditText)findViewById(R.id.editText_name_r);
                EditText pas=(EditText)findViewById(R.id.editText_password_r);
                EditText idstr=(EditText)findViewById(R.id.editText_id);
                global_data.name=user.getText().toString();
                global_data.password=pas.getText().toString();
                global_data.id=idstr.getText().toString();
                if(TextUtils.isEmpty(global_data.name))
                {
                    Toast.makeText(activity_register.this, "请输入用户名！", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(global_data.password))
                {
                    Toast.makeText(activity_register.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                }
                else
                {

                        global_data.my_net.got = false;
                        new Thread(new Runnable() {
                            public void run() {
                                global_data.my_net.sendMsg("a00000" , global_data.name + "\n" + global_data.password);
                            }
                        }).start();
                        int wti = 0;
                        Toast.makeText(activity_register.this, "。。注册中。。", Toast.LENGTH_SHORT).show();
                        while (!global_data.my_net.got && wti < 10) {
                            wti++;
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (global_data.my_net.message.equals("succeed")) {
                            Toast.makeText(activity_register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor edit = getSharedPreferences("data_login", MODE_PRIVATE).edit();
                            edit.putString("name", global_data.name);
                            edit.putString("password", global_data.password);
                            edit.commit();
                            Intent intent = new Intent(activity_register.this, welcome_activity.class);
                            startActivity(intent);
                            finish();
                        }
                        else if (global_data.my_net.message.equals("NULL"))
                        {
                            Toast.makeText(activity_register.this, "注册失败,请检查用户名，密码后重试！", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(activity_register.this,"注册失败,请检查网络后重试！",Toast.LENGTH_SHORT).show();
                            global_data.my_net.good_net=false;
                        }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_register, menu);
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
