package org.example;

import java.sql.*;
import java.util.Scanner;

public class Aerolinea {

    public static void main(String[] args) {

        try {
            Connection connection = MySQLConnection.obtenerConexion();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduce el número de vuelo: ");
            String numeroVuelo = scanner.nextLine();
            //IB-SP-4567

            //Llamada metodos
            //mostrarInformacionGeneral(connection);
            //mostrarInformacionPasajeros(connection);
            mostrarPasajerosPorVuelo(connection, numeroVuelo);
            //insertarVuelo(connection, "IB-NEW-123", "01/01/24 10:00", "ORIGEN", "DESTINO", 50, 50, 100, 20);
            //borrarVuelo(connection, "IB-NEW-123");
            //modificarFumadoresANoFumadores(connection, "IB-SP-4567");*/

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarInformacionGeneral(Connection connection) throws SQLException {
        try {
            String sql = "SELECT * FROM vuelos";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Imprimir resultados
            while (resultSet.next()) {
                String codVuelo = resultSet.getString("COD_VUELO");
                String fechaSalida = resultSet.getString("HORA_SALIDA");
                String procedencia = resultSet.getString("PROCEDENCIA");
                String destino = resultSet.getString("DESTINO");

                System.out.println("FECHA: " + fechaSalida + ", CODIGO: " + codVuelo +
                        ", Peocedencia: " + procedencia + ", Destino: " + destino);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void mostrarInformacionPasajeros(Connection connection) throws SQLException {
        try {
            String sql = "SELECT * FROM pasajeros";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int num = resultSet.getInt("NUM");
                String codVuelo = resultSet.getString("COD_VUELO");
                String tipoPlaza = resultSet.getString("TIPO_PLAZA");
                String fumador = resultSet.getString("FUMADOR");

                System.out.println("Número: " + num + ", Vuelo: " + codVuelo +
                        ", Plaza: " + tipoPlaza + ", Fumador: " + fumador);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void mostrarPasajerosPorVuelo(Connection connection, String codigoVuelo) {
        String sql = "SELECT * FROM PASAJEROS WHERE COD_VUELO = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, codigoVuelo);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Número: " + resultSet.getInt("NUM") +
                            ", Tipo de Plaza: " + resultSet.getString("TIPO_PLAZA") +
                            ", Fumador: " + resultSet.getString("FUMADOR"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertarVuelo(Connection connection, String codigoVuelo, String horaSalida, String destino,
                                      String procedencia, int plazasFumador, int plazasNoFumador,
                                      int plazasTurista, int plazasPrimera) throws SQLException {
        // Implementar lógica para insertar un nuevo vuelo
    }

    private static void borrarVuelo(Connection connection, String codigoVuelo) throws SQLException {
        // Implementar lógica para borrar un vuelo por su código
    }

    private static void modificarFumadoresANoFumadores(Connection connection, String codigoVuelo) throws SQLException {
        // Implementar lógica para modificar vuelos de fumadores a no fumadores
    }
}
