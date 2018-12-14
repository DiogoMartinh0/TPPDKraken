package Models;

import java.io.Serializable;

public class DataUserLogin implements Serializable {
    // por causa da passagem de dados via TCP/UDP
    static final long serialVersionUID = 1L;

    private int userID;
    private String username;
    private String password;
    private String name;
    private boolean authenticationSuccessful;
    private String sharedFolder;

    public DataUserLogin(String username, String password) {
        this.userID = -1;
        this.username = username;
        this.password = password;
        this.authenticationSuccessful = false;
        this.sharedFolder = null;
    }

    public DataUserLogin(Integer userID, String username, String password, String name, String sharedFolder) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.authenticationSuccessful = true;
        this.sharedFolder = sharedFolder;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public String getName() {
        return name;
    }

    public boolean getAuthenticationSuccessful(){
        return authenticationSuccessful;
    }

    public String getSharedFolder() {
        return sharedFolder;
    }
}
