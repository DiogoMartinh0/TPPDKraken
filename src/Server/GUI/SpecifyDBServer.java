package Server.GUI;

import Database.DBManager;
import Server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpecifyDBServer extends JFrame {
    private JPanel panel1;
    private JTextField ipTF;
    private JButton okButton;
    private JTextField portTF;

    public SpecifyDBServer() {
        super("TP PD Server");

        this.add(panel1);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack(); // adapta a janela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(this.getWidth() + 200, this.getHeight());
        this.invalidate();
        this.setVisible(true);


        okButton.addActionListener(listener -> {
            try {
//                DBManager.initDatabase(); // ler os botões para obter UI


                new ServerUserInterface();
                this.dispose();

            } catch (Exception e1) {
                JOptionPane.showMessageDialog( getParent(), "Action error.\nException: " + e1.toString());
            }
        });

    }
}
