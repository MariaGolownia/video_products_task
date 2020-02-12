package by.godel.video.app.dao.pool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionSQL {
    public static final int MAX_CONNECTIONS_SIZE = 100;
    private static final String SERVER_NAME = "localhost";
    private static final String MY_DATA_BASE = "video_products_app";
    private static final String URL = "jdbc:postgresql://" + SERVER_NAME + "/" + MY_DATA_BASE;
    private static final String USER_SQL_NAME = "postgres";
    private static final String USER_SQL_PASSWORD = "QAZwsxedc!@#";

    public Connection getConnectionToDB () {
        // Регистрация драйвера
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection=null;
        try {
            Class.forName("org.postgresql.Driver");
            connectionPool.init("org.postgresql.Driver",
                    URL, USER_SQL_NAME, USER_SQL_PASSWORD,
                    1,MAX_CONNECTIONS_SIZE, 10000);
            connection = connectionPool.getConnection();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void connectToDB () {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection=null;
        try {
            connectionPool.init("java.sql.Driver",
                    URL, USER_SQL_NAME, USER_SQL_PASSWORD,
                    1,MAX_CONNECTIONS_SIZE, 10000);
            connection = connectionPool.getConnection();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

}
