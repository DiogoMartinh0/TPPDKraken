package Client;

import Communication.NetworkObjects;
import Models.DataUserLogin;
import Models.ServerDetails;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTCPHandler {
    private Socket socketToServer;

    public ClientTCPHandler(ServerDetails serverDetails){
        socketToServer = NetworkObjects.getTCPSocketTo(serverDetails.getIp(), serverDetails.getPortaTCP());
    }

    private void isSocketValid(){
        if (socketToServer == null || socketToServer.isClosed())
            throw new IllegalStateException("socketToServer is on an invalid state...");
    }

    public DataUserLogin authenticateUser(DataUserLogin dUL) throws IOException, ClassNotFoundException {
        isSocketValid();

        ObjectOutputStream out = new ObjectOutputStream(socketToServer.getOutputStream());
        out.writeObject(dUL);
        out.flush();

        ObjectInputStream in = new ObjectInputStream(socketToServer.getInputStream());
        dUL = (DataUserLogin) in.readObject();

        return dUL.getAuthenticationSuccessful() ? dUL : null;
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
