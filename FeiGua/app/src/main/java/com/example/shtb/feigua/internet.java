package com.example.shtb.feigua;

import android.util.Log;
import android.widget.Toast;

import java.io.*;
import java.net.*;

public final class internet {
    static Socket client;
    static String site;
    static int port;
    static String message;
    boolean got=false;
    static boolean good_net=false;
    public internet()  {

    }
    public void init(String xsite, int xport)
    {
        site=xsite;
        port=xport;
        link();
    }
public void link()
{
    good_net=true;
    client = new Socket();
    try {
        client.connect(new InetSocketAddress(site, port), 1000);
        message="succeed!";
        good_net=true;
    }
    catch (UnknownHostException e) {
        message="unknown host!";
        good_net=false;
    }
    catch (IOException e) {
        message="IO failed!";
        e.printStackTrace();
        good_net=false;
    }
}
    public void sendMsg(String choose,String mess){
        message="";
        mess+="\n";
        int len=mess.getBytes().length;
        if(good_net) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());
                out.write(choose);
                char[] tem;
                tem=new char[4];
                tem[0]= (char) (len&0x000000ff);
                tem[1]= (char) (len&0x0000ff00);
                tem[2]= (char) (len&0x00ff0000);
                tem[3]= (char) (len&0xff000000);
                out.write(tem);
                out.write(mess);
                out.flush();
                message = in.readLine();
            } catch (IOException e) {
                good_net = false;
                e.printStackTrace();
            }
            got = true;
        }
        else
        {
            link();
            sendMsg(choose, mess);
        }
    }
    public void closeSocket(){
        try{
            client.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}