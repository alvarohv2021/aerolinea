package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Connection conexion = MySQLConnection.obtenerConexion();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
