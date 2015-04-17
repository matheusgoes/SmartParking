/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.xbeemonitor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alba
 */
public class ConnectionFactory {

    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:8889/Xbee";
    String user = "root";
    String pass = "root";
    private static ConnectionFactory connectionFactory = null;

    public ConnectionFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
