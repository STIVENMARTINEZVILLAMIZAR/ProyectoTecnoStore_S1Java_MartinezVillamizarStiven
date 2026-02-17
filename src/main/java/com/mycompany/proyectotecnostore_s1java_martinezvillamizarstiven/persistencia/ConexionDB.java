package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class ConexionDB {
        private static final String URL = "jdbc:mysql://localhost:3306/tecnostore_db?useSSL=false&serverTimezone=UTC";
        private static final String USUARIO = "campus2023";
        private static final String PASSWORD = "campus2023";
        private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        
        private static Connection conexion;
    
        /**
         * Establece conexión con la BD
         */
        public static Connection conectar() {
            try {
                // Cargar driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Crear conexión
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("✅ Conexión exitosa con la base de datos.");
                return conexion;
                
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Error: Driver MySQL no encontrado.");
                System.err.println("   Asegúrate de tener mysql-connector-java en las dependencias.");
                e.printStackTrace();
                return null;
            } catch (SQLException e) {
                System.err.println("❌ Error de conexión SQL: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    
        /**
         * Obtiene la conexión actual
         */
        public static Connection obtenerConexion() {
            if (conexion == null) {
                conectar();
            }
            return conexion;
        }
    
        /**
         * Cierra la conexión
         */
        public static void cerrarConexion() {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    System.out.println("✅ Conexión cerrada.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }