package Client.GUI;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainApplication extends JFrame{
    private JTree tree1;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField messageField;
    private JButton sendButton;
    private JButton historyButton;
    private JProgressBar processProgressBar;
    private JLabel processLabel;
    private JButton chDirButton;
    private JLabel label_welcomeUser;

    public MainApplication(String username) {

        super("TP PD Client User Interface");

        this.add(panel1);


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //this.setLocationRelativeTo(null);
        //this.setResizable(false);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int largura = gd.getDisplayMode().getWidth();
        int altura = gd.getDisplayMode().getHeight();

        this.setSize((int)(largura * 0.8), (int)(altura * 0.8));
        this.setLocationRelativeTo(null);
        //this.pack(); // adapta a janela



        this.label_welcomeUser.setText(String.format("Hello %s!", username));



        this.invalidate();
        this.setVisible(true);


        File root = new File("" + System.getProperty("user.home") + "/Documents"); // https://www.tutorialspoint.com/java/java_file_class.htm
        FileTreeModel fileTreeModel = new FileTreeModel(root);
        tree1.setModel(fileTreeModel);

        tree1.addTreeSelectionListener(TreeSelectionListener -> {
            try {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
                if (!node.isLeaf())
                    return;
                else {
                    Object o = node.getUserObject();
                    //processLabel.setText(o.toString());
                    System.out.println(o);
                }
            } catch (Exception e) {e.printStackTrace();}
        });

        chDirButton.addActionListener(listener -> {
            File newRoot = new File(chDirButton.getText());
            if (newRoot.exists()) {
                FileTreeModel newFileTreeModel = new FileTreeModel(newRoot);
                tree1.setModel(newFileTreeModel);
            } else {
                JOptionPane.showMessageDialog( getParent(), "Change Directory Error:\nFolder not found.");
            }
        });
    }
}


