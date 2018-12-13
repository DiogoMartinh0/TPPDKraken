package Server;

import Database.DBManager;
import Models.DataUserLogin;
import Models.UserDetails;
import Util.PDUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCPHandler extends Thread implements Observer {
    private static final int TIMEOUT = 200000; // ms

    private ServerSocket localTCPSocket;

    public ServerTCPHandler(int portaTCP) throws IOException {
        localTCPSocket = new ServerSocket(portaTCP);
        localTCPSocket.setSoTimeout(TIMEOUT);
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        PDUtils.printLineSync("Thread pool iniciada!");

        while (!localTCPSocket.isClosed()){
            try {
                Socket currentClient = localTCPSocket.accept();
                executor.execute(() -> {
                    DataUserLogin dataUserLogin;
                    ObjectInputStream objectInputStream;
                    ObjectOutputStream objectOutputStream;

                    try{
                        objectInputStream = new ObjectInputStream(currentClient.getInputStream());
                        Object readObject = objectInputStream.readObject();

                        if (readObject instanceof DataUserLogin){
                            dataUserLogin = (DataUserLogin)readObject;

                            dataUserLogin = verifyUserIdentity(dataUserLogin);

                            objectOutputStream = new ObjectOutputStream(currentClient.getOutputStream());
                            objectOutputStream.writeObject(dataUserLogin);
                            objectOutputStream.flush();

                            if (dataUserLogin.getAuthenticationSuccessful()){
                                updateUserIP(dataUserLogin.getUserID(), currentClient.getInetAddress().toString().replace("/", ""));
                                updateUserTCPPort(dataUserLogin.getUserID(), currentClient.getLocalPort());
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        PDUtils.printLineSync("An error occurred when trying to authenticate a user, I'm shutting down his connection!");
                        // TODO: atualizar os dados da base de dados para terminar este cliente
                        e.printStackTrace();
                        try {
                            currentClient.close();
                        } catch (IOException e1) {
                            PDUtils.printLineSync("An error occurred while trying to shutdown the previously mentioned user!");
                        }
                        return;
                    }

                    while (!currentClient.isClosed()){
                        try {
                            Object readObject = objectInputStream.readObject();
                            if (readObject instanceof DataUserLogin) {
                                DataUserLogin dUL = (DataUserLogin) readObject;
                                verifyUserIdentity(dUL);
                            }
                        } catch (SocketException e){
                            PDUtils.printLineSync("A client has disconnected!");
                            //e.printStackTrace();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DataUserLogin verifyUserIdentity(DataUserLogin dUL){
        DataUserLogin dataUserLogin = DBManager.fetchUserInformation(dUL);
        if (dataUserLogin != null){
            // Utilizador autenticado com sucesso, modifica a flag de retorno
            DBManager.resetNumeroDeFalhas(dataUserLogin.getUserID());
            return dataUserLogin;
        }

        return dUL;
    }



    private void updateUserIP(int userID, String ip){
        DBManager.updateUserIP(userID, ip);
    }

    private void updateUserTCPPort(int userID, int portaTCP){
        DBManager.updateUserTCPPort(userID, portaTCP);
    }

    private void updateUserSharedDirectory(int userID, int portaUDP){
        DBManager.updateUserUDPPort(userID, portaUDP);
    }


    @Override
    public void update(Observable o, Object arg) {

    }
}