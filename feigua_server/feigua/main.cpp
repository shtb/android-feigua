#include<iostream>
#include<string>
#include"inte_link.h"
#include"database.h"
#include"interfaces.h"

using namespace std;

int main(int argc,char**argv)
{
      if(argc==2)
            my_net.ip_addr=argv[1];
      my_data.init();
      my_net.init();
      int dsa=0;
      while(!byebye)
      {
            dsa++;
            usleep(1000000);
            if(dsa%100==0)
            {
                  my_data.user_login("timeout","link");
            }
      }
      return 0;
}
