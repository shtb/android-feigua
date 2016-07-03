#include "database.h"

using namespace std;

database::database()
{
      //ctor
}

void database::create_tables()
{
      if (mysql_query(conn, "create table user(name varchar(50) primary key,pword varchar(50),teacher boolean);"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
      if (mysql_query(conn, "create table lesson(name varchar(100) primary key,place varchar(100),date varchar(100),intr varchar(200),teacher varchar(50),foreign key(teacher)references user(name));"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
      if (mysql_query(conn, "create table choose(user varchar(50),lesson varchar(100),primary key(user,lesson),foreign key (user)references user(name),foreign key (lesson)references lesson(name));"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
      if (mysql_query(conn, "create table titles(id int primary key AUTO_INCREMENT,title varchar(100),intr varchar(300),user varchar(50),foreign key(user)references user(name));"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
      if (mysql_query(conn, "create table answers(id int primary key AUTO_INCREMENT,answer varchar(300),user varchar(50),titleid int,foreign key(user)references user(name),foreign key(titleid)references titles(id));"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
      if (mysql_query(conn, "create table comment(user varchar(50),comment varchar(1000));"))
      {
            fprintf(stderr, "%s\n", mysql_error(conn));
      }
}

void database::init()
{
      const char *server_args[] =
      {
            "this_program",
            "--datadir=.",
            "--key_buffer_size=32M"
      };
      if (mysql_server_init(sizeof(server_args) / sizeof(char *),(char**)server_args, (char**)server_groups))
      {
            cout<<"database init failed!!!!!!"<<endl;
            exit(1);
      }
      conn = mysql_init(NULL);
      if (!mysql_real_connect(conn, "localhost","shtb2", "12344321", "feigua", 0, NULL, 0))
      {
            cerr<<  mysql_error(conn)<<endl;
            exit(1);
      }
      create_tables();
}

string database::user_register(string name,string pword)
{
      string ss="NULL";
      string sql="insert into user values('"+name+"','"+pword+"',false);";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed";
      }
      return ss;
}

string database::user_login(string name,string pword)
{
      string ss="NULL";
      string sql="select * from user where name='"+name+"' and pword='"+pword+"';";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            res = mysql_use_result(conn);
            if((row = mysql_fetch_row(res)) != NULL)
            {
                  ss="succeed";
            }
            mysql_free_result(res);
      }
      return ss;
}

string database::comment(string name,string comment)
{
      string ss="NULL";
      string sql="insert into comment values('"+name+"','"+comment+"');";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed";
      }
      return ss;
}

string database::choose_class(string user,string lesson)
{
      string ss="NULL";
      string sql="insert into choose values('"+user+"','"+lesson+"');";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed";
      }
      return ss;
}

string database::get_classes()
{
      string ss="NULL";
      string sql="select * from lesson;";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="lesson\t";
            res = mysql_use_result(conn);
            while((row = mysql_fetch_row(res)) != NULL)
            {
                  for(int i=0;i<5;++i)
                  {
                        if(row[i]!=NULL)
                              ss+=row[i];
                        else
                              ss+="NULL";
                        ss+="\t";
                  }
            }
            mysql_free_result(res);
      }
      return ss;
}

string database::my_classes(string user)
{
      string ss="NULL";
      string sql=" select name,place,date,intr,teacher from choose,lesson where user='"+user+"'and name=lesson;";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            res = mysql_use_result(conn);
            ss="lesson\t";
            while((row = mysql_fetch_row(res)) != NULL)
            {
                  for(int i=0;i<5;++i)
                  {
                        if(row[i]!=NULL)
                              ss+=row[i];
                        else
                              ss+="NULL";
                        ss+="\t";
                  }
            }
            mysql_free_result(res);
      }
      return ss;
}

string database::up_class(string name,string place,string date,string intr,string teacher)
{
      string ss="NULL";
      string sql=" insert into lesson values('"+name+"','"+place+"','"+date+"','"+intr+"','"+teacher+"');";
      cout<<sql<<endl;
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed";
      }
      return ss;
}

string database::up_title(string title,string answer,string user)
{
      string ss="NULL";
      string sql="insert into titles (title,intr,user) values('"+title+"','"+answer+"','"+user+"');";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed\t";
            sql="select id from titles where title='"+title+"'and intr='"+answer+"'and user='"+user+"';";
            if (mysql_query(conn, sql.c_str()))
            {
                  cerr<< mysql_error(conn)<<endl;
            }
            else
            {
                  res = mysql_use_result(conn);
                  while((row = mysql_fetch_row(res)) != NULL)
                  {
                        if(row[0]!=NULL)
                              ss+=row[0];
                        else
                              ss+="NULL";
                        ss+="\t";
                  }
                  mysql_free_result(res);
            }
      }
      return ss;
}

string database::up_answer(string answer,string id,string user)
{
      string ss="NULL";
      string sql="insert into answers (answer,user,titleid) values('"+answer+"','"+user+"',"+id+");";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            ss="succeed";
      }
      return ss;
}

string database::get_title(string like)
{
      string ss="NULL";
      string sql="select title,user,id from titles where title like '%"+like+"%';";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            res = mysql_use_result(conn);
            ss="title\t";
            while((row = mysql_fetch_row(res)) != NULL)
            {
                  for(int i=0;i<3;++i)
                  {
                        if(row[i]!=NULL)
                              ss+=row[i];
                        else
                              ss+="NULL";
                        ss+="\t";
                  }
            }
            mysql_free_result(res);
      }
      return ss;
}

string database::get_answer(string id)
{
      string ss="NULL";
      string sql="select intr,user from titles where id="+id+";";
      if (mysql_query(conn, sql.c_str()))
      {
            cerr<< mysql_error(conn)<<endl;
      }
      else
      {
            res = mysql_use_result(conn);
            ss="title\t";
            while((row = mysql_fetch_row(res)) != NULL)
            {
                  for(int i=0;i<2;++i)
                  {
                        if(row[i]!=NULL)
                              ss+=row[i];
                        else
                              ss+="NULL";
                        ss+="\t";
                  }
            }
            mysql_free_result(res);
            cout<<"SSSSSSS"<<ss<<endl;
            sql="select answer,user from answers where titleid="+id+";";
            if (mysql_query(conn, sql.c_str()))
            {
                  cerr<< mysql_error(conn)<<endl;
            }
            else
            {
                  res = mysql_use_result(conn);
                  while((row = mysql_fetch_row(res)) != NULL)
                  {
                        for(int i=0;i<2;++i)
                        {
                              if(row[i]!=NULL)
                                    ss+=row[i];
                              else
                                    ss+="NULL";
                              ss+="\t";
                        }
                  }
                  mysql_free_result(res);
            }
            cout<<"SSSSSSS"<<ss<<endl;
      }
      return ss;
}

database::~database()
{
      //dtor
}


