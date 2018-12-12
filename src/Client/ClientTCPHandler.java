package Client;

import Communication.NetworkObjects;
import Models.ServerDetails;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTCPHandler {
    private Socket socketToServer;

    private ClientTCPHandler(ServerDetails serverDetails){
        socketToServer = NetworkObjects.getTCPSocketTo(serverDetails.getIp(), serverDetails.getPortaTCP());
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<ServerDetails> getServerList(){
        try {
            Socket sock = new Socket("127.0.0.1", 18000);

            ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
            return (ArrayList<ServerDetails>)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
