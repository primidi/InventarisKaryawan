package inventariskaryawan.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    String url, username, password;
    Connection conn = null;

    public DatabaseConnection() throws SQLException {
        this.url = "jdbc:mysql://localhost/inventaris";
        this.username = "root";
        this.password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection succeed!");
        } catch (ClassNotFoundException error) {
            System.out.println("Connection failed, " + error.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
