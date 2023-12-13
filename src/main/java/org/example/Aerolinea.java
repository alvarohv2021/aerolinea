package org.example;

import java.sql.*;
import java.util.Scanner;

public class Aerolinea {
    static Statement statement = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String codigoVuelo = null;
        String horaSalida = null;

        try {
            Connection connection = MySQLConnection.obtenerConexion();

            System.out.println("---------------------------------------------\n" +
                    "Escoja una opción:\n" +
                    "0. Salir del Programa\n" +
                    "1. Mostrar Información General\n" +
                    "2. Mostara Información de los Pasajeros\n" +
                    "3. Ver los Pasajeros de un Vuelo\n" +
                    "4. Insertar Nuevo Vuelo\n" +
                    "5. Borrar vuelo Introducido Previamente\n" +
                    "6. Convertir Vuelos de Fumadores en no Fumadores\n" +
                    "---------------------------------------------");


            int opcion = scanner.nextInt();

            switch (opcion) {
                case 0:
                    System.out.println("Adios");
                    break;
                case 1:
                    mostrarInformacionGeneral(connection);
                    break;
                case 2:
                    mostrarInformacionPasajeros(connection);
                    break;
                case 3:
                    System.out.println("Introduce el número de vuelo (ex: IB-D5-347): ");

                    Scanner scanner3 = new Scanner(System.in);
                    codigoVuelo = scanner3.nextLine();

                    mostrarPasajerosPorVuelo(connection, codigoVuelo);
                    break;
                case 4:

                    Scanner scanner4 = new Scanner(System.in);
                    System.out.println("Introduce el número de vuelo (ex: IB-D5-347): ");
                    codigoVuelo = scanner4.nextLine();

                    boolean diaCorrecto = false;
                    int diaSalidaInt = 0;
                    while (!diaCorrecto) {

                        System.out.println("Introduce el dia de salida, debe estar entre 1 y 31: ");
                        String diaSalida = scanner4.nextLine();
                        diaSalidaInt = Integer.parseInt(diaSalida);

                        if (diaSalidaInt >= 1 && diaSalidaInt <= 31) {

                            diaCorrecto = true;

                        } else {
                            System.out.println("Dia incorrecto");
                        }
                    }

                    boolean mesCorrecto = false;
                    int mesSalidaInt = 0;
                    while (!mesCorrecto) {

                        System.out.println("Introduce el mes de salida, debe estar entre 1 y 12: ");
                        String mesSalida = scanner4.nextLine();
                        mesSalidaInt = Integer.parseInt(mesSalida);

                        if (mesSalidaInt >= 1 && mesSalidaInt <= 12) {

                            mesCorrecto = true;


                        } else {
                            System.out.println("Mes incorrecto");
                        }
                    }

                    boolean añoCorrecto = false;
                    int añoSalidaInt = 0;
                    while (!añoCorrecto) {

                        System.out.println("Introduce el año de salida, debe ser en formato 20XX: ");
                        String añoSalida = scanner4.nextLine();
                        añoSalidaInt = Integer.parseInt(añoSalida);

                        if (añoSalidaInt > 1999 && añoSalidaInt < 3000) {

                            añoCorrecto = true;


                        } else {
                            System.out.println("Año incorrecto");
                        }
                    }

                    horaSalida = diaSalidaInt + "/" + mesSalidaInt + "/" + añoSalidaInt;

                    insertarVuelo(connection, codigoVuelo, horaSalida, "Estambul", "Palma", 50, 100, 125, 25);
                    break;
                case 5:
                    borrarVuelo(connection);
                    break;
                case 6:
                    modificarFumadoresANoFumadores(connection, "\"IB-SP-4567\"");
                    break;
            }

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
        String sql = "INSERT INTO vuelos (COD_VUELO, HORA_SALIDA, DESTINO, PROCEDENCIA, PLAZAS_FUMADOR, PLAZAS_NO_FUMADOR, PLAZAS_Turista, PLAZAS_PRIMERA)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, codigoVuelo);
        preparedStatement.setString(2, horaSalida);
        preparedStatement.setString(3, destino);
        preparedStatement.setString(4, procedencia);
        preparedStatement.setInt(5, plazasFumador);
        preparedStatement.setInt(6, plazasNoFumador);
        preparedStatement.setInt(7, plazasTurista);
        preparedStatement.setInt(8, plazasPrimera);

        preparedStatement.executeUpdate();
        System.out.println("Nuevo vuelo insertado correctamente");
    }

    private static void borrarVuelo(Connection connection) throws SQLException {
        statement = connection.createStatement();

        Scanner codigoVueloABorrar = new Scanner(System.in);
        System.out.println("Escriba el codigo del vuelo que desea borrar:\n");
        String vueloABorrar = "\"" + codigoVueloABorrar.nextLine() + "\"";

        String sql1 = "DELETE FROM vuelos WHERE COD_VUELO = " + vueloABorrar;

        statement.addBatch(sql1);
        statement.executeBatch();

        System.out.println("Se ha borrado correctamente");
    }

    private static void modificarFumadoresANoFumadores(Connection connection, String codigoVuelo) throws SQLException {
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        int resultadoSuma = 0;

        System.out.println("Estas seguro de que quieres realizar el cambio? S(Si) | N(No)\n");
        Scanner scanner = new Scanner(System.in);
        String confirmacion = scanner.nextLine();
        if (confirmacion.equals("S")) {
            String sql = "SELECT SUM(PLAZAS_FUMADOR + PLAZAS_NO_FUMADOR) AS resultado_suma FROM `vuelos` WHERE COD_VUELO = " + codigoVuelo;


            preparedStatement1 = connection.prepareStatement(sql);
            resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                resultadoSuma = resultSet.getInt("resultado_suma");
            }

            hacerUpdate(connection, codigoVuelo, resultadoSuma);
        }

    }

    private static void hacerUpdate(Connection connection, String codigoVuelo, int suma) throws SQLException {
        String sql = "UPDATE vuelos SET PLAZAS_FUMADOR = ?, PLAZAS_NO_FUMADOR = ? WHERE COD_VUELO = "+codigoVuelo;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, suma);
            preparedStatement.executeUpdate();
            System.out.println("Update realizada con exito");
        }
    }
}
