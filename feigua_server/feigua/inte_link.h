#ifndef INET_LINK
#define INET_LINK

#include<iostream>
#include<thread>
#include<string>
#include<sstream>
#include<sys/types.h>
#include<sys/socket.h>
#include<sys/un.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<errno.h>
#include<string.h>
#include<functional>

class inte_link
{
    public:
        std::string  ip_addr;

        inte_link();
        void init();
        void accept_loop();
        void begin_recv_loop(int* connect);
        void parse(std::string &choose, std::stringstream& ss,int);
        void send_to(std::string my_message,int socket);
        virtual ~inte_link();
    protected:
    private:
        const static int TRANS_MAX_LENGTH=10240;
        static int server_socket;
        sockaddr_in server_addr;
};

#endif // INET_LINK
