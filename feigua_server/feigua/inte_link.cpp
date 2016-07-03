#include "inte_link.h"
#include"interfaces.h"
using std::cout;
using std::cerr;
using std::cin;
using std::endl;
using std::thread;
using std::string;
using std::stringstream;

int inte_link::server_socket;

void thread_tem(inte_link*inte,int*connect)
{
      inte->begin_recv_loop(connect);
}

void thread_tem_accept(inte_link*inte)
{
      inte->accept_loop();
}

inte_link::inte_link()
{
      //ctor
}

void inte_link::accept_loop()
{
      sockaddr_in connect_addr;
      while(1)
      {
            socklen_t tem_len=sizeof(connect_addr);
            int connect_socket = accept(server_socket, (struct sockaddr*)&connect_addr,&tem_len);
            if(connect_socket== -1)
            {
                  cerr<<strerror(errno)<<endl;
            }
            char buff[100];
            string tem_s=inet_ntop(AF_INET, &connect_addr.sin_addr, buff, 100);
            cout<<tem_s<<" linked!"<<endl;
            thread recv_thread(thread_tem,this,&connect_socket);
            recv_thread.detach();
      }
}

void inte_link::init()
{
      cout<<"init internet server!"<<endl;

      server_socket=socket(AF_INET,SOCK_STREAM,0);
      if(server_socket<0)
      {
            string s=strerror(errno);
            cerr<<"socket:"<<s<<endl;
            exit(0);
      }

      int sock_op=1;
      setsockopt( server_socket, SOL_SOCKET, SO_REUSEADDR, &sock_op, sizeof(sock_op) );

      server_addr.sin_family = AF_INET;
      inet_pton(AF_INET, ip_addr.c_str(), &server_addr.sin_addr);
      server_addr.sin_port = htons(32954);

      if( bind(server_socket, (struct sockaddr*)&server_addr, sizeof(server_addr)) == -1)
      {
            string s=strerror(errno);
            cerr<<"bind:"<<s<<endl;
            exit(0);
      }
      if( listen(server_socket, 10) == -1)
      {
            string s=strerror(errno);
            cerr<<"listen:"+s;
            exit(0);
      }
      thread accept_thread(thread_tem_accept,this);
      accept_thread.detach();
      cout<<"Waiting for connect--"<<endl;
}

void inte_link::parse(std::string &choose, std::stringstream& ss,int connect)
{
      cout<<"GET:"<<choose<<endl;
      switch(choose[0])
      {
      case('a')://register
      {
            string name,pword;
            getline(ss,name);
            getline(ss,pword);
            send_to(my_data.user_register(name,pword),connect);
      }
            break;
      case('b')://login
      {
            string name,pword;
            getline(ss,name);
            getline(ss,pword);
            send_to(my_data.user_login(name,pword),connect);
      }
            break;
      case('c')://comment
      {
            string name,comment;
            getline(ss,name);
            getline(ss,comment);
            send_to(my_data.comment(name,comment),connect);
      }
            break;
      case('d')://choose_class
      {
            string user,lesson;
            getline(ss,user);
            getline(ss,lesson);
            send_to(my_data.choose_class(user,lesson),connect);
      }
            break;
      case('e')://get_classes
      {
            send_to(my_data.get_classes(),connect);
      }
            break;
      case('f')://my_classes
      {
            string name;
            getline(ss,name);
            send_to(my_data.my_classes(name),connect);
      }
            break;
      case('g')://up_class
      {
            string name,place,date,reward,teacher;
            getline(ss,name);
            getline(ss,place);
            getline(ss,date);
            getline(ss,reward);
            getline(ss,teacher);
            send_to(my_data.up_class(name,place,date,reward,teacher),connect);
      }
            break;
      case('h')://up_title
      {
            string title,answer,user;
            getline(ss,title);
            getline(ss,answer);
            getline(ss,user);
            send_to(my_data.up_title(title,answer,user),connect);
      }
            break;
      case('i')://up_answer
      {
            string answer,id,user;
            getline(ss,answer);
            getline(ss,id);
            getline(ss,user);
            send_to(my_data.up_answer(answer,id,user),connect);
      }
            break;
      case('j')://get_answer
      {
            string id;
            getline(ss,id);
            send_to(my_data.get_answer(id),connect);
      }
            break;
      case('k')://get_title
      {
            string like;
            getline(ss,like);
            send_to(my_data.get_title(like),connect);
      }
            break;
      default:
            cout<<"ERROR:"<<ss.str()<<endl;
            break;
      }
      ss.clear();
      ss.str("");
}

void inte_link::begin_recv_loop(int* my_connect_x)
{
      int my_connect = *my_connect_x;
      char recv_buff[TRANS_MAX_LENGTH];

      std::stringstream source;
      unsigned int sour_len = 0;
      string choose;
      char head[7];
      unsigned int mess_len = -1;
      char* c_mess;
      std::stringstream s_mess;
      head[6] = '\0';
      int state = 0; // 0-ok,1-zhan,2-fen;
      int get_n;
      while(true)
      {
            if(state == 0)
            {
                  get_n = recv(my_connect, recv_buff, TRANS_MAX_LENGTH, 0);
                  if(get_n <= 0)
                  {
                        cout<<"connect losed!"<<endl;
                        break;
                  }
                  source.write(recv_buff, get_n);
                  while(source.str().length()- source.tellg()<10)
                  {
                        get_n = recv(my_connect, recv_buff, TRANS_MAX_LENGTH, 0);
                        source.write(recv_buff, get_n);
                  }
                  source.read(head, 6);
                  choose = head;
                  source.read((char*)&mess_len, sizeof(int));
                  // get-length
                  sour_len = source.str().length();
                  if(sour_len == mess_len + 10)
                  {
                        c_mess = new char[mess_len];
                        source.read(c_mess, mess_len);
                        s_mess.write(c_mess, mess_len);
                        parse(choose, s_mess,my_connect);
                        delete c_mess;
                        source.clear();
                        source.str("");
                        sour_len = 0;
                  }
                  else if(sour_len > mess_len + 10)
                  {
                        state = 1;
                  }
                  else
                  {
                        state = 2;
                  }
            }
            else if(state == 1)
            {
                  while(sour_len > mess_len + 10)
                  {
                        c_mess = new char[mess_len];
                        source.read(c_mess, mess_len);
                        s_mess.write(c_mess, mess_len);
                        parse(choose, s_mess,my_connect);
                        delete c_mess;

                        while(source.str().length()- source.tellg()<10)
                        {
                              get_n = recv(my_connect, recv_buff, TRANS_MAX_LENGTH, 0);
                              source.write(recv_buff, get_n);
                        }
                        source.read(head, 6);
                        choose = head;
                        source.read((char*)&mess_len, sizeof(int));
                        // get-length
                        sour_len = source.str().length() - source.tellg() + 10;
                  }
                  if(sour_len == mess_len + 10)
                  {
                        c_mess = new char[mess_len];
                        source.read(c_mess, mess_len);
                        s_mess.write(c_mess, mess_len);
                        parse(choose, s_mess,my_connect);
                        delete c_mess;
                        source.clear();
                        source.str("");
                        sour_len = 0;
                        state = 0;
                  }
                  else
                  {
                        state = 2;
                  }
            }
            else if(state == 2)
            {
                  get_n = recv(my_connect, recv_buff, TRANS_MAX_LENGTH, 0);
                  if(get_n <= 0)
                  {
                        cout<<"connect losed!"<<endl;
                        break;
                  }
                  source.write(recv_buff, get_n);
                  sour_len = source.str().length() - source.tellg() + 10;
                  if(sour_len == mess_len + 10)
                  {
                        c_mess = new char[mess_len];
                        source.read(c_mess, mess_len);
                        s_mess.write(c_mess, mess_len);
                        parse(choose, s_mess,my_connect);
                        delete c_mess;
                        source.clear();
                        source.str("");
                        sour_len = 0;
                        state = 0;
                  }
                  else if(sour_len > mess_len + 10)
                  {
                        state = 1;
                  }
                  else
                  {
                        state = 2;
                  }
            }
            if(mess_len>1048576)
            {
                  cout<<"ERROR:TOO LONG MESSAGE! "<<mess_len<<"\033[0m"<<endl;
                  stringstream ds;
                  ds.write((char*)&mess_len,4);
                  cout<<ds.str()<<ds.str().length()<<endl;
                  cout<<"choose:"<<choose<<endl;
                  break;
            }
      }
      close(my_connect);
}

void inte_link::send_to(string my_message,int socket)
{
      int bytes = send(socket,(my_message+"\n").c_str(),my_message.length()+1,0);
      if (bytes <0)
      {
            cerr<<"send message WRONG!";
            usleep(1000000);
      }
      if(bytes>1000)
      {
            cout<<bytes<<endl;
      }
}

inte_link::~inte_link()
{
      //dtor
}
