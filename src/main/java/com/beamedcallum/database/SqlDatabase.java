package com.beamedcallum.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

        try {
            testConnection();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connection to database established!");
    }

    private void testConnection() throws InterruptedException {
        Connection con = null;
        try {
            con = acquireConnection();
            ResultSet res = con.prepareStatement("select 1 as 'data';").executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to communicate with database!");
            Thread.sleep(5 * 1000);
            testConnection();
        }
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
