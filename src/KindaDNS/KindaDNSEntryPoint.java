package KindaDNS;

import Models.ServerDetails;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class KindaDNSEntryPoint {

    private static Integer portaRespostas = 18000;
    private static Integer portaRegistos = portaRespostas + 1;

    private static Thread threadRespostas;
    private static Thread threadRegistos;

    private static ServerSocket socketRespostas;
    private static ServerSocket socketRegistos;

    public static void main(String [] args){
        KDNS_DBManager.initDatabase();

        System.out.println("\n");

        threadRespostas = new Thread(() -> {
            try {
                socketRespostas = new ServerSocket(portaRespostas);
                Socket newConnection;

                System.out.println("Thread para respostas iniciada");

                while (!socketRespostas.isClosed()){
                    try{
                        newConnection = socketRespostas.accept();

                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(newConnection.getOutputStream());
                        objectOutputStream.writeObject(KDNS_DBManager.getServerList());
                        objectOutputStream.flush();

                        newConnection.close();
                    } catch (SocketException e){
                        System.out.println("SocketException para o socket [socketRespostas]");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        threadRegistos = new Thread(() -> {
            try {
                socketRegistos = new ServerSocket(portaRegistos);
                Socket newConnection;

                System.out.println("Thread para registos iniciada");

                while (!socketRegistos.isClosed()){
                    try{
                        newConnection = socketRegistos.accept();

                        ObjectInputStream objectInputStream = new ObjectInputStream(newConnection.getInputStream());
                        ServerDetails newServer = (ServerDetails)objectInputStream.readObject();

                        newServer.setId(KDNS_DBManager.registerServer(newServer.getNome(), newServer.getIp(), newServer.getPortaTCP(), newServer.getPortaUDP()));

                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(newConnection.getOutputStream());
                        objectOutputStream.writeObject(newServer);
                        objectOutputStream.flush();

                        newConnection.close();
                    } catch (SocketException e){
                        System.out.println("SocketException para o socket [socketRegistos]");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        });

        threadRespostas.start();
        threadRegistos.start();

        Thread waitForThreadsToFinish = new Thread(() -> {
            String input;
            Scanner scanner = new Scanner(System.in);
            do{
                input = scanner.nextLine();
                System.out.println(input);
            } while (!input.toLowerCase().trim().equals("q") && !input.toLowerCase().trim().equals("quit"));


            threadRespostas.interrupt();
            threadRegistos.interrupt();
            System.exit(0);
        });
        waitForThreadsToFinish.start();

        try {
            threadRespostas.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException para a thread [threadRespostas]");
        }

        try {
            threadRegistos.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException para a thread [threadRegistos]");
        }

        try {
            socketRespostas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            socketRegistos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
