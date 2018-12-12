package KindaDNS;

import Database.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KDNS_DBManager extends DBManager {

    public static int registerServer(String serverName, String ip, Integer portaTCP, Integer portaUDP){
        try {
            preparedStatement = conn.prepareStatement(String.format("SELECT * FROM %s.servidor WHERE ip LIKE ? LIMIT 1", databaseName));
            preparedStatement.setString(1, ip);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // já existe um servidor com este ip
                try {
                    preparedStatement = conn.prepareStatement(String.format("UPDATE %s.servidor SET nome = ?, portaTCP = ?, portaUDP = ? WHERE ip LIKE ?", databaseName));
                    preparedStatement.setString(1, serverName);
                    preparedStatement.setInt(2, portaTCP);
                    preparedStatement.setInt(3, portaUDP);
                    preparedStatement.setString(4, ip);

                    preparedStatement.executeUpdate();
                    return resultSet.getInt(1); // devolve o id do servidor na db
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                // não existe um servidor com este ip, vou registá-lo
                try {
                    preparedStatement = conn.prepareStatement(String.format("INSERT INTO %s.servidor (nome, ip, porta) VALUES (?, ?, ?)", databaseName));

                    preparedStatement.setString(1, serverName);
                    preparedStatement.setString(2, ip);
                    preparedStatement.setInt(3, portaTCP);
                    preparedStatement.setInt(4, portaUDP);

                    preparedStatement.executeUpdate();

                    preparedStatement = conn.prepareStatement(String.format("SELECT * FROM %s.servidor WHERE ip LIKE ? LIMIT 1", databaseName));
                    preparedStatement.setString(1, ip);

                    resultSet = preparedStatement.executeQuery();
                    if (resultSet.next())
                        return resultSet.getInt(1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
