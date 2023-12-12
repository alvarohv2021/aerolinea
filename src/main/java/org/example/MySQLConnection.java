package org.example;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/aerolinea";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static java.sql.Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
