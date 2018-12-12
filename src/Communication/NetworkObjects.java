package Communication;

import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.net.*;
import java.util.Objects;

public class NetworkObjects {
    private static final int DEFAULT_TIMEOUT = 20 * 1000; //segundos to micro
    private static final int DEFAULT_SERVER_TCP_PORT = 25777;
    private static final int DEFAULT_SERVER_UDP_PORT = 26777;


    public static Socket getTCPSocketTo(String ip, Integer port){
        return getTCPSocketTo(ip, port, DEFAULT_TIMEOUT);
    }

    public static Socket getTCPSocketTo(String ip, Integer port, Integer timeout){
        try {
            Socket x = new Socket(ip, port);
            x.setSoTimeout(timeout);
            return x;
        } catch (IOException e) {
            return null;
        }
    }



    public static DatagramSocket getUDPSocket(){
        try {
            return new DatagramSocket();
        } catch (SocketException e) {
            return null;
        }
    }

    public static boolean sendDatagramPacket(DatagramPacket datagramPacket){
        try {
            Objects.requireNonNull(getUDPSocket()).send(datagramPacket);
            return true;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }



    public static DatagramSocket getUDPServerSocket(){
        return getUDPServerSocket(DEFAULT_SERVER_UDP_PORT, DEFAULT_TIMEOUT);
    }

    public static DatagramSocket getUDPServerSocket(Integer port){
        return getUDPServerSocket(port, DEFAULT_TIMEOUT);
    }

    public static DatagramSocket getUDPServerSocket(Integer port, Integer timeout){
        try {
            DatagramSocket datagramSocket = new DatagramSocket(port);
            datagramSocket.setSoTimeout(timeout);
            return datagramSocket;
        } catch (SocketException e) {
            return null;
        }
    }



    public static ServerSocket getTCPServerSocket(){
        return getTCPServerSocket(DEFAULT_SERVER_TCP_PORT, DEFAULT_TIMEOUT);
    }

    public static ServerSocket getTCPServerSocket(Integer port){
        return getTCPServerSocket(port, DEFAULT_TIMEOUT);
    }

    public static ServerSocket getTCPServerSocket(Integer port, Integer timeout) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(timeout);
            return serverSocket;
        } catch (IOException e) {
            return null;
        }
    }
}
