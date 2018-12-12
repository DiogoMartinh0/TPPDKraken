package Models;

import java.io.Serializable;

public class DataUserLogin implements Serializable {
    // por causa da passagem de dados via TCP/UDP
    static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean authenticationSuccessful;
    private String sharedFolder;

    public DataUserLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.authenticationSuccessful = false;
        this.sharedFolder = null;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() { return password; }

    public boolean getAuthenticationSuccessful(){
        return authenticationSuccessful;
    }

    public void setAuthenticationSuccessful(boolean newValue){
        this.authenticationSuccessful = newValue;
    }

    public String getSharedFolder() {
        return sharedFolder;
    }

    public void setSharedFolder(String sharedFolder) {
        this.sharedFolder = sharedFolder;
    }
}
