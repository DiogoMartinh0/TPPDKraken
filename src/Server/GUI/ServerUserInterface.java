package Server.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerUserInterface extends JFrame {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton shutdownButton;


    //Create thread to process Client requests



    public ServerUserInterface() {

        this.add(panel1);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack(); // adapta a janela
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(this.getWidth() + 200, this.getHeight());
        this.invalidate();
        this.setVisible(true);

        // connect to DB
        // init thread

        shutdownButton.addActionListener(listener -> {
            this.dispose();
        });
    }
}

class ThreadServidor extends Thread {



}
