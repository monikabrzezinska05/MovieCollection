package DAL.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private SQLServerDataSource dataSource;

    public DatabaseConnector(){

        DatabaseConnectionInfo connectionInfo = new DatabaseConnectionInfo(
                Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config.properties"
        );

        dataSource = new SQLServerDataSource();
        dataSource.setServerName(connectionInfo.getProperty("DB_HOST"));
        dataSource.setDatabaseName(connectionInfo.getProperty("DB_NAME"));
        dataSource.setUser(connectionInfo.getProperty("DB_USER"));
        dataSource.setPassword(connectionInfo.getProperty("DB_PASSWORD"));
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(Integer.parseInt(connectionInfo.getProperty("DB_PORT")));
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {

        DatabaseConnector databaseConnector = new DatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());
        }
    }
}
