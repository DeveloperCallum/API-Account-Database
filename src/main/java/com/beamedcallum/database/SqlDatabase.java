package com.beamedcallum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDatabase extends Database {
    private final String ip;
    private final String port;
    private final String dbName;
    private final String user;
    private final String password;

    public SqlDatabase(String ip, String port, String dbName, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    @Override
    protected Connection acquireConnection() throws SQLException {
        try {
            synchronized (this) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(getConnectionURL(ip, port, dbName, user, password));
            }
        } catch (ClassNotFoundException e) {
            //TODO: Custom Exception
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getConnectionURL(String ip, String port, String dbName, String user, String password) {
        return "jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?characterEncoding=utf8&" + "user=" + user + "&password=" + password;
    }
}
