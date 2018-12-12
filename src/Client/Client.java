package Client;


import Client.GUI.LoginForm;

import java.net.Socket;

public class Client {

    LoginForm formLogin;
    Socket mySocket;

    public Client(String args[]){
        formLogin = new LoginForm(args[0], mySocket);
    }
}
