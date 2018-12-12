package Models;

public class UserDetails {

    protected Integer userID;
    protected String username;
    protected String password;
    protected String nome;
    protected String sharedFolder;

    public UserDetails(Integer userID, String username, String password, String nome, String sharedFolder) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.sharedFolder = sharedFolder;
    }

    public Integer getUserID() { return userID; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() { return nome; }
}
