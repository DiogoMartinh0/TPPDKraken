package Client;


import Client.GUI.LoginForm;
import Client.GUI.MainApplication;

import java.net.Socket;

public class Client {

    LoginForm formLogin;
    Socket mySocket;

    ClientTCPHandler clientTCPHandler;


    public Client(String args[]){
        formLogin = new LoginForm(clientTCPHandler, args[0]);
        //new MainApplication("Diogo");
    }
}
