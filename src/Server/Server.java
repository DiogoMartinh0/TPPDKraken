package Server;

import Database.DBManager;
import Models.ServerDetails;

import java.io.*;
import java.net.Socket;
import java.util.Observable;

public class Server extends Observable {
    private ServerDetails localServerDetails;

    private ServerTCPHandler serverTCPHandler;

    public Server(String serverName, String serverIP, int serverPort) {
        // Registar este servidor no DNS
        localServerDetails = new ServerDetails(-1, serverName, serverIP, serverPort, serverPort + 1);
        if (!connectToDNS()){
            System.out.println("Invalid server details, aborted!");
            System.exit(-1);
        }

        // Iniciar o "fornecedor" de dados da DB
        DBManager.initDatabase();


        // Iniciar TCPHandler
        try {
            serverTCPHandler = new ServerTCPHandler(serverPort);
            serverTCPHandler.start();
        } catch (IOException e) {
            System.out.println("Couldn't start TCPHandler, more info:");
            e.printStackTrace();
        }


        // TODO: Iniciar UDPHandler



        // Registar Observers
        this.addObserver(serverTCPHandler);

        System.out.println("Server started! Server details -> " + localServerDetails.toString());
    }

    private boolean connectToDNS(){
        try {
            Socket socketParaDNS = new Socket("127.0.0.1", 18001);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketParaDNS.getOutputStream());
            objectOutputStream.writeObject(localServerDetails);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socketParaDNS.getInputStream());
            localServerDetails = (ServerDetails)objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Se o ID retornado for != -1 quer dizer que ele conseguiu as informações do servidor através do DNS
        return localServerDetails.getId() != -1;
    }

    public void run(){



        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* // thread principal que recebe os pedidos dos clientes
        while(true){
            try {
                //  um socket para receber x cliente
                // aceita um pedido de ligacao Tcp do cliente
                socketUser = server.accept();

                // Inicia uma thread destinada a tratar da comunicacao com o master
                // id dos clientes++;
                receiveClients = new Thread(new Server(socketUser));
                receiveClients.start();

            }catch(IOException e){
                System.out.println("An exception ocorred while waiting for connection of user: \n\t" + e);
            }

        }*/
    }

    public void shutdown() {
        // TODO: Criar um método dentro dos handlers para rebentar com os sockets
        // TODO: Fazer joins das threads
    }
}
