package pl.chylu.VoldeBot;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DbConnect {
    final Connection connection;
    private final Dotenv config;

    DbConnect() throws SQLException {
        config = Dotenv.configure().load();
        String url = config.get("URLDB");
        String username = config.get("USERDB");
        String password = config.get("PASSWORDDB");
        connection = DriverManager.getConnection(url, username, password);
    }
}