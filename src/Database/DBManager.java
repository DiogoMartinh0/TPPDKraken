package Database;

import Models.DataUserLogin;
import Models.ServerDetails;
import Models.UserDetails;
import Server.Server;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    protected static Connection conn = null;
    protected static PreparedStatement preparedStatement = null;

    private static final String host = "162.218.211.242";
    private static final String port = "3306";
    protected static final String databaseName = "progdist";
    private static final String username = "codemaster";
    private static final String password = "codemasterpw";
    private static final String connectionParams = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static boolean isConnected = false;

    public static void initDatabase(){
        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        System.out.println("Connecting to database...");

        try {
            conn = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s%s", host, port, databaseName, connectionParams), username, password);
            isConnected = true;
            System.out.println("Connected to database!");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new IllegalStateException("Cannot connect the database!", ex);
        }
    }

    public static void registerNewUser(String username, String password){
        try {
            preparedStatement = conn.prepareStatement(String.format("INSERT INTO %s.user (username, password) VALUES (?, ?)", databaseName));

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataUserLogin fetchUserInformation(DataUserLogin dataUserLogin){
        try {
            preparedStatement = conn.prepareStatement(String.format("SELECT * FROM %s.user WHERE username LIKE ? AND password LIKE ? LIMIT 1", databaseName));
            preparedStatement.setString(1, dataUserLogin.getUsername());
            preparedStatement.setString(2, dataUserLogin.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()){
                // resultado da query é vazio
                return null;
            }else{
                // Devolve um objecto preenchido
                return new DataUserLogin(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(8));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void updateConnectionDetails(int userID, String ip, int portaTCP, int portaUDP, String sharedDirectory){
        updateUserIP(userID, ip);
        updateUserTCPPort(userID, portaTCP);
        updateUserUDPPort(userID, portaUDP);
        updateUserSharedDirectory(userID, sharedDirectory);
        resetNumeroDeFalhas(userID);
    }

    public static void updateUserIP(int userID, String ip){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET ip = ? WHERE userID = ?", databaseName));
            preparedStatement.setString(1, ip);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserTCPPort(int userID, int portaTCP){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET portaTCP = ? WHERE userID = ?", databaseName));
            preparedStatement.setInt(1, portaTCP);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserUDPPort(int userID, int portaUDP){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET portaUDP = ? WHERE userID = ?", databaseName));
            preparedStatement.setInt(1, portaUDP);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserSharedDirectory(int userID, String newDirectory){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET sharedDirectory = ? WHERE userID = ?", databaseName));
            preparedStatement.setString(1, newDirectory);
            preparedStatement.setInt(2, userID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void incrementarNumeroDeFalhas(int userID){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET nFalhas = nFalhas + 1 WHERE userID = ?", databaseName));
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetNumeroDeFalhas(int userID){
        try {
            preparedStatement = conn.prepareStatement(String.format("UPDATE %s.user SET nFalhas = 0 WHERE userID = ?", databaseName));
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarFicheiro(int userID, String fileName){
        throw new NotImplementedException();
    }

    public static void removerFicheiro(int userID, String fileName){
        throw new NotImplementedException();
    }

    public static void adicionarFicheiro(int userID, ArrayList<String> fileNames){
        throw new NotImplementedException();
    }

    public static ArrayList<ServerDetails> getServerList(){
        try {
            preparedStatement = conn.prepareStatement(String.format("SELECT * FROM %s.servidor", databaseName));
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<ServerDetails> servers = new ArrayList<>();

            while(resultSet.next()){
                servers.add(new ServerDetails(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getInt(5)
                ));
            }

            return servers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getTables() {
        try {
            preparedStatement = conn.prepareStatement(String.format("SHOW tables"));
            ResultSet resultSet = preparedStatement.executeQuery();

            //return resultSet.;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
