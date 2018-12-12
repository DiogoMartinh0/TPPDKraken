package Server;

import Server.GUI.SpecifyDBServer;

import java.util.Scanner;


public class ServerEntryPoint {

    public static void main(String args[]){
        //System.out.println("Hello from ServerEntryPoint");
        //new SpecifyDBServer();

        Server s = new Server("localhost", "127.0.0.1", 5000);

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        while(continuar){
            String linha = scanner.nextLine().toLowerCase();

            if (linha.contains("quit"))
                continuar = false;
        }

        s.shutdown();
    }
}
