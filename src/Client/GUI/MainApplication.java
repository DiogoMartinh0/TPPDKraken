package Client.GUI;

import Models.DataUserLogin;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class MainApplication extends JFrame{
    private JTree sharedFolderContent;
    private JPanel panel1;
    private JTextArea textArea1;
    private JTextField messageField;
    private JButton sendButton;
    private JButton historyButton;
    private JProgressBar processProgressBar;
    private JLabel processLabel;
    private JButton chDirButton;
    private JLabel label_welcomeUser;
    private JTextField textField_currentDirectory;
    private JButton button1;


    public MainApplication(DataUserLogin dataUserLogin) {
        super("TP PD Client User Interface");

        this.add(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize((int)(gd.getDisplayMode().getWidth() * 0.8), (int)(gd.getDisplayMode().getHeight() * 0.8));
        this.setLocationRelativeTo(null);


        this.label_welcomeUser.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(3, 3, 3, 10)));
        this.label_welcomeUser.setText(String.format("Hello %s (%s)!", dataUserLogin.getUsername(), dataUserLogin.getName()));

        if (dataUserLogin.getSharedFolder() != null){
            updateTreeModel(new File(dataUserLogin.getSharedFolder())); // https://www.tutorialspoint.com/java/java_file_class.htm
        }else{
            int dialogResult = JOptionPane.showConfirmDialog(null, "You're not sharing a folder, do you want to select one now?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION){
                updateTreeModel(selectSharedDirectory());
            }else{
                // TODO: se o user não partilhar uma pasta, deixamos ficar ou?...
            }
        }

        //this.setResizable(false);
        //this.pack(); // adapta a janela
        this.invalidate();
        this.setVisible(true);

        sharedFolderContent.addTreeSelectionListener(TreeSelectionListener -> {
            try {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) sharedFolderContent.getLastSelectedPathComponent();
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
            updateTreeModel(selectSharedDirectory());

            /*File newRoot = new File(textField_currentDirectory.getText());
            if (newRoot.exists()) {
                FileTreeModel newFileTreeModel = new FileTreeModel(newRoot);
                sharedFolderContent.setModel(newFileTreeModel);
            } else {
                JOptionPane.showMessageDialog(getParent(), "Change Directory Error:\nFolder not found.");
            }*/
        });
    }

    private File selectSharedDirectory(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (textField_currentDirectory.getText() != null)
            fileChooser.setCurrentDirectory(new File(textField_currentDirectory.getText()));
        else
            fileChooser.setCurrentDirectory(new File("" + System.getProperty("user.home") + "/Documents"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // TODO: update database with new shared directory
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    private void updateTreeModel(File sharedDirectory){
        if (sharedDirectory != null){
            System.out.println("Updated folder: " + sharedDirectory.getAbsolutePath());

            this.textField_currentDirectory.setText(sharedDirectory.getAbsolutePath());

            FileTreeModel fileTreeModel = new FileTreeModel(sharedDirectory);
            sharedFolderContent.setModel(fileTreeModel);
            sharedFolderContent.setEnabled(true);
        }else {
            sharedFolderContent.setEnabled(false);
        }
    }
}


