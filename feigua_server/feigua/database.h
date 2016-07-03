#ifndef DATABASE_H
#define DATABASE_H

#include<mysql/mysql.h>
#include <stdlib.h>
#include<stdio.h>
#include<iostream>
#include<string>
#include<sstream>

using namespace std;

static const char *server_groups[] =
{
    "embedded",
    "server",
    "this_program_SERVER",
    (char *)NULL
};

class database
{
	public:
		MYSQL *conn;
		MYSQL_RES *res;
		MYSQL_ROW row;

		database();
		void create_tables();
		void init();
		string user_register(string name,string pword);
		string user_login(string name,string pword);
		string comment(string name,string comment);
		string choose_class(string user,string lesson);
		string get_classes();
		string my_classes(string user);
            string up_class(string name,string place,string date,string reward,string teacher);

            string up_title(string title,string answer,string user);
            string up_answer(string answer,string id,string user);
            string get_title(string like);
            string get_answer(string id);
		virtual ~database();
	protected:
	private:
};

#endif // DATABASE_H
