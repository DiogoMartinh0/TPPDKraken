package Client.GUI;

import Client.ClientTCPHandler;
import Models.DataUserLogin;
import Communication.NetworkObjects;
import Database.DBManager;
import Models.ServerDetails;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class LoginForm extends JFrame{
    private JComboBox serverIP;
    private JTextField LoginPassword;
    private JTextField LoginUsername;
    private JButton loginButton;
    private JTextField RegUsername;
    private JTextField RegName;
    private JTextField RegPassword;
    private JTextField repeatPassword;
    private JButton registerButton;
    private JPanel myPanel;
    private JPanel serverIPPanel;
    private JPanel registerPanel;
    private JPanel loginPanel;
    private ClientTCPHandler clientTCPHandler;

    private final String defaultComboBoxString = "Select a server";

    ArrayList<ServerDetails> alSD;

    public LoginForm(ClientTCPHandler clientTCPHandler, String arg) {
        super("TP PD Client Login");
        this.clientTCPHandler = clientTCPHandler;

        for (Component cmp : myPanel.getComponents()) {
            if (!(cmp instanceof JRootPane)) {
                cmp.setVisible(true);
                cmp.setEnabled(false);
            }
        }

        myPanel.setVisible(true);
        add(myPanel);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);           //Não faço ideia quanto deste código é inútil.
        this.pack(); // adapta a janela
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setSize(this.getWidth() + 200, this.getHeight());
        this.invalidate();
        this.setVisible(true);                                                // /


        serverIP.addItem("Getting servers...");
        serverIP.setEnabled(false);

        LoginUsername.setText("diogo");


        alSD = new ArrayList<>();
        if (arg.equals("debug")){
            DBManager.initDatabase();
            alSD = DBManager.getServerList();
        }else{
            alSD = ClientTCPHandler.getServerList();
        }

        serverIP.removeAllItems();
        serverIP.addItem(defaultComboBoxString);

        try {
            for (ServerDetails sv : alSD)
                //serverIP.addItem(String.format("%s [%s:%s]", sv.getName(), sv.getIp(), sv.getPortaTCP().toString()));
                serverIP.addItem(sv.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }

        serverIP.setEnabled(true);

        //                  \/ LISTENERS \/

        serverIP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTCPHandler(alSD.get(serverIP.getSelectedIndex() - 1));
            }
        });

        registerButton.addActionListener(listener -> { //  - <Register Button>
            /*
            // MUST REGISTER THROUGH ONLINE SERVER

            if (!validateServer()) return;
            if (!validadeRegisterFields()) return;
            ServerDetails chosenServer = alSD.get(serverIP.getSelectedIndex());


            DataUserLogin dUL = new DataUserLogin();
            try {
                dUL.setName(RegUsername.getText());
                dUL.setPassword(RegPassword.getText());
                dUL.setName(RegName.getText());
                if (RegPassword.equals(repeatPassword))
                    dUL.setPassword(RegPassword.getText());

                dUL.setServerAddress(InetAddress.getByName(chosenServer.getIp()));
                dUL.setServerPort(chosenServer.getPorta());

                dUL.setAuthenticated(false);

                try {
                    socket = new Socket(dUL.getServerAddress(),dUL.getServerPort());//tcp é assim
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket.setSoTimeout(20*1000);


                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());


            } catch (Exception e1) {
                JOptionPane.showMessageDialog( getParent(), "Authentication error.\nException: " + e1.toString());
            }
            */

        });

        loginButton.addActionListener(listener -> {
            // MUST BE CONNECTED TO SERVER FOR SERVER TO TALK TO DB
            authenticateUser();
        });// - </Login Button>
    }

    private void createTCPHandler(ServerDetails serverDetails){
        clientTCPHandler = new ClientTCPHandler(serverDetails);
    }

    private void authenticateUser(){
        if (!validadeLoginFields()) return;

        try {
            DataUserLogin dUL = new DataUserLogin(LoginUsername.getText(), LoginPassword.getText());
            if ((dUL = clientTCPHandler.authenticateUser(dUL)) != null){
                new MainApplication(dUL);
                this.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(getParent(), "Authentication error.");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(getParent(), "Authentication error.\nException: " + e1.toString());
        }
    }

    private boolean validateServer(){
        return !Objects.requireNonNull(serverIP.getSelectedItem()).toString().equalsIgnoreCase(defaultComboBoxString);
    }

    private boolean validadeRegisterFields(){
        if (RegUsername.getText().length() < 4){
            JOptionPane.showMessageDialog(getParent(), "Username must have at least 4 characters!");
            return false;
        }

        if (RegName.getText().lastIndexOf(' ') == -1){
            JOptionPane.showMessageDialog(getParent(), "Your name must be composed of at least two names!");
            return false;
        }

        if (RegPassword.getText().length() < 6){
            JOptionPane.showMessageDialog(getParent(), "Your password must have at least 6 characters");
            return false;
        }

        if (!RegPassword.getText().equals(repeatPassword.getText())){
            JOptionPane.showMessageDialog(getParent(), "Your passwords do now match!");
            return false;
        }

        return true;
    }

    private boolean validadeLoginFields() {
        if (LoginUsername.getText().length() < 4){
            JOptionPane.showMessageDialog(getParent(), "Username must have at least 4 characters!");
            return false;
        }

        if (LoginPassword.getText().length() < 6){
            JOptionPane.showMessageDialog(getParent(), "Your password must have at least 6 characters");
            return false;
        }

        return true;
    }
}
