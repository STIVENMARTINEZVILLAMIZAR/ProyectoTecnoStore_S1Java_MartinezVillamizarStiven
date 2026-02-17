package com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.vista;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador.GestorCelulares;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador.GestorClientes;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.controlador.GestorVentas;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.dao.ReporteFinancieroDAO;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Celular;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Cliente;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.DetalleVenta;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.modelo.Venta;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.persistencia.ConexionDB;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.servicio.ReporteFinancieroService;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades.ReporteUtils;
import com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.utilidades.Validador;

public class Menu {
    private Scanner sc;
    private GestorCelulares gestorCelulares;
    private GestorClientes gestorClientes;
    private GestorVentas gestorVentas;

    public Menu() {
        this.sc = new Scanner(System.in);
        this.gestorCelulares = new GestorCelulares();
        this.gestorClientes = new GestorClientes();
        this.gestorVentas = new GestorVentas();
    }

    public void mostrarMenuPrincipal() {
        boolean salir = false;

        while (!salir) {
            limpiarPantalla();
            System.out.println("========== TECNOSTORE - SISTEMA DE GESTIÓN ==========");
            System.out.println("1. Gestionar Celulares");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Realizar Venta");
            System.out.println("4. Reportes");
            System.out.println("5. Reporte de Ingresos por Marca");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            try {
                int opcion = sc.nextInt();
                sc.nextLine();

                if (!Validador.validarOpcion(opcion, 1, 6)) {
                    System.out.println("Opción inválida. Intente de nuevo.");
                    pausa();
                    continue;
                }

                switch (opcion) {
                    case 1:
                        menuCelulares();
                        break;
                    case 2:
                        menuClientes();
                        break;
                    case 3:
                        menuVentas();
                        break;
                    case 4:
                        menuReportes();
                        break;
                    case 5:
                        menuReporteFinanciero();
                        break;
                    case 6:
                        System.out.println("Gracias por usar TECNOSTORE. ¡Hasta luego!");
                        salir = true;
                        break;
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
                pausa();
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage());
                pausa();
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                sc.nextLine();
                pausa();
            }
        }
    }

    private void menuCelulares() throws SQLException {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            System.out.println("========== GESTIÓN DE CELULARES ==========");
            System.out.println("1. Registrar celular");
            System.out.println("2. Listar celulares");
            System.out.println("3. Actualizar celular");
            System.out.println("4. Eliminar celular");
            System.out.println("5. Volver");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarCelular();
                    break;
                case 2:
                    listarCelulares();
                    break;
                case 3:
                    actualizarCelular();
                    break;
                case 4:
                    eliminarCelular();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

            if (opcion != 5) {
                pausa();
            }
        }
    }

    private void registrarCelular() throws SQLException {
        System.out.println("\n--- Registrar Celular ---");
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();
        System.out.print("Precio: $");
        double precio = sc.nextDouble();
        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();
        System.out.print("Sistema Operativo (iOS/Android): ");
        String so = sc.nextLine();
        System.out.print("Gama (Baja/Media/Alta): ");
        String gama = sc.nextLine();

        try {
            gestorCelulares.registrar(marca, modelo, precio, stock, so, gama);
            System.out.println("✓ Celular registrado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarCelulares() throws SQLException {
        System.out.println("\n========== LISTADO DE CELULARES ==========");
        List<Celular> celulares = gestorCelulares.listar();

        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }

        for (Celular c : celulares) {
            System.out.println(c.getId() + " | " + c.getMarca() + " " + c.getModelo()
                    + " | $" + String.format("%.2f", c.getPrecio())
                    + " | Stock: " + c.getStock() + " | " + c.getGama());
        }
    }

    private void actualizarCelular() throws SQLException {
        System.out.println("\n--- Actualizar Celular ---");
        System.out.print("ID del celular a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Celular c = gestorCelulares.obtenerPorId(id);
        if (c == null) {
            System.out.println("✗ Celular no encontrado.");
            return;
        }

        System.out.println("Datos actuales: " + c.getMarca() + " " + c.getModelo());
        System.out.print("Nuevo precio ($): ");
        double nuevoPrecio = sc.nextDouble();
        System.out.print("Nuevo stock: ");
        int nuevoStock = sc.nextInt();
        sc.nextLine();

        try {
            gestorCelulares.actualizar(id, c.getMarca(), c.getModelo(), nuevoPrecio, nuevoStock,
                    c.getSistemaOperativo(), c.getGama());
            System.out.println("✓ Celular actualizado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void eliminarCelular() throws SQLException {
        System.out.println("\n--- Eliminar Celular ---");
        System.out.print("ID del celular a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Celular c = gestorCelulares.obtenerPorId(id);
        if (c == null) {
            System.out.println("✗ Celular no encontrado.");
            return;
        }

        System.out.println("¿Está seguro que desea eliminar " + c.getMarca() + " " + c.getModelo()
                + "? (S/N)");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            gestorCelulares.eliminar(id);
            System.out.println("✓ Celular eliminado exitosamente!");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void menuClientes() throws SQLException {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            System.out.println("========== GESTIÓN DE CLIENTES ==========");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Actualizar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Volver");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    actualizarCliente();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

            if (opcion != 5) {
                pausa();
            }
        }
    }

    private void registrarCliente() throws SQLException {
        System.out.println("\n--- Registrar Cliente ---");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Identificación (CC, TI, etc.): ");
        String identificacion = sc.nextLine();
        System.out.print("Correo: ");
        String correo = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        try {
            gestorClientes.registrar(nombre, identificacion, correo, telefono);
            System.out.println("✓ Cliente registrado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void listarClientes() throws SQLException {
        System.out.println("\n========== LISTADO DE CLIENTES ==========");
        List<Cliente> clientes = gestorClientes.listar();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente cl : clientes) {
            System.out.println(cl.getId() + " | " + cl.getNombre() + " | "
                    + cl.getIdentificacion() + " | " + cl.getCorreo() + " | " + cl.getTelefono());
        }
    }

    private void actualizarCliente() throws SQLException {
        System.out.println("\n--- Actualizar Cliente ---");
        System.out.print("ID del cliente a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente cl = gestorClientes.obtenerPorId(id);
        if (cl == null) {
            System.out.println("✗ Cliente no encontrado.");
            return;
        }

        System.out.println("Datos actuales: " + cl.getNombre());
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo correo: ");
        String correo = sc.nextLine();
        System.out.print("Nuevo teléfono: ");
        String telefono = sc.nextLine();

        try {
            gestorClientes.actualizar(id, nombre, correo, telefono);
            System.out.println("✓ Cliente actualizado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    private void eliminarCliente() throws SQLException {
        System.out.println("\n--- Eliminar Cliente ---");
        System.out.print("ID del cliente a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Cliente cl = gestorClientes.obtenerPorId(id);
        if (cl == null) {
            System.out.println("✗ Cliente no encontrado.");
            return;
        }

        System.out.println("¿Está seguro que desea eliminar a " + cl.getNombre() + "? (S/N)");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            gestorClientes.eliminar(id);
            System.out.println("✓ Cliente eliminado exitosamente!");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void menuVentas() throws SQLException {
        System.out.println("\n--- Realizar Venta ---");
        System.out.print("ID del cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        Cliente cliente = gestorClientes.obtenerPorId(idCliente);
        if (cliente == null) {
            System.out.println("✗ Cliente no existe!");
            pausa();
            return;
        }

        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("\nIngrese los celulares a vender (ID 0 para terminar):");

        List<DetalleVenta> detalles = new ArrayList<>();
        boolean agregarMas = true;

        while (agregarMas) {
            System.out.print("\nID Celular (0 para terminar): ");
            int idCelular = sc.nextInt();
            sc.nextLine();

            if (idCelular == 0) {
                agregarMas = false;
                break;
            }

            Celular celular = gestorCelulares.obtenerPorId(idCelular);
            if (celular == null) {
                System.out.println("✗ Celular no encontrado.");
                continue;
            }

            System.out.println("Celular: " + celular.getMarca() + " " + celular.getModelo() + " - $"
                    + celular.getPrecio());
            System.out.print("Cantidad: ");
            int cantidad = sc.nextInt();
            sc.nextLine();

            if (cantidad <= 0) {
                System.out.println("✗ Cantidad debe ser mayor a 0");
                continue;
            }

            if (cantidad > celular.getStock()) {
                System.out.println("✗ Stock insuficiente. Disponible: " + celular.getStock());
                continue;
            }

            double subtotal = celular.getPrecio() * cantidad;
            DetalleVenta detalle = new DetalleVenta(0, 0, idCelular, cantidad, subtotal);
            detalles.add(detalle);

            System.out.println("✓ Producto agregado. Subtotal: $" + String.format("%.2f", subtotal));
            System.out.print("¿Desea agregar otro celular? (S/N): ");
            String respuesta = sc.nextLine().trim();

            if (respuesta.equalsIgnoreCase("N")) {
                agregarMas = false;
            }
        }

        if (!detalles.isEmpty()) {
            try {
                int idVenta = gestorVentas.registrarVenta(idCliente, detalles);
                double total = detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum() * 1.19;
                System.out.println("\n========== VENTA REGISTRADA ==========");
                System.out.println("✓ Venta exitosa!");
                System.out.println("ID Venta: " + idVenta);
                System.out.println("Cliente: " + cliente.getNombre());
                System.out.println("Total de productos: " + detalles.size());
                System.out.println("Subtotal: $" + String.format("%.2f",
                        detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum()));
                System.out.println("IVA (19%): $" + String.format("%.2f",
                        detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum() * 0.19));
                System.out.println("Total (con IVA): $" + String.format("%.2f", total));
                System.out.println("=====================================");
            } catch (IllegalArgumentException e) {
                System.out.println("✗ Error: " + e.getMessage());
            }
        } else {
            System.out.println("Venta cancelada. Sin productos agregados.");
        }

        pausa();
    }

    private void menuReportes() throws SQLException, IOException {
        boolean volver = false;

        while (!volver) {
            limpiarPantalla();
            System.out.println("========== REPORTES ==========");
            System.out.println("1. Stock bajo (< 5 unidades)");
            System.out.println("2. Ventas por mes");
            System.out.println("3. Generar reporte general");
            System.out.println("4. Volver");
            System.out.print("Opción: ");

            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    reporteStockBajo();
                    break;
                case 2:
                    reporteVentasMes();
                    break;
                case 3:
                    ReporteUtils.generarReporteVentas(gestorVentas);
                    break;
                case 4:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

            if (opcion != 4) {
                pausa();
            }
        }
    }

    private void reporteStockBajo() throws SQLException {
        List<Celular> celulares = gestorCelulares.obtenerStockBajo();
        ReporteUtils.mostrarStockBajo(celulares);
    }

    private void reporteVentasMes() throws SQLException {
        System.out.println("\n--- Ventas por Mes ---");
        System.out.print("Mes (1-12): ");
        int mes = sc.nextInt();
        System.out.print("Año: ");
        int anio = sc.nextInt();
        sc.nextLine();

        if (mes < 1 || mes > 12) {
            System.out.println("✗ Mes inválido.");
            return;
        }

        List<Venta> ventas = gestorVentas.obtenerVentasMes(mes, anio);
        double totalMes = gestorVentas.obtenerTotalVentasMes(mes, anio);

        System.out.println("\n========== VENTAS " + mes + "/" + anio + " ==========");

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas para este período.");
            return;
        }

        for (Venta v : ventas) {
            System.out.println("ID: " + v.getId() + " | Cliente: " + v.getIdCliente() + " | Fecha: "
                    + v.getFecha() + " | Total: $" + String.format("%.2f", v.getTotal()));
        }

        System.out.println("\nTOTAL MES: $" + String.format("%.2f", totalMes));
    }

    /**
     * Menú para acceder a Reportes Financieros por Marca
     */
    private void menuReporteFinanciero() {
        Connection conexion = null;
        try {
            limpiarPantalla();
            System.out.println("⏳ Conectando a la base de datos...");

            // Obtener conexión desde ConexionDB
            conexion = ConexionDB.conectar();

            if (conexion == null) {
                System.out.println("❌ Error: No se pudo conectar a la base de datos.");
                pausa();
                return;
            }

            System.out.println("✅ Conexión exitosa.\n");

            // Crear DAO y Servicio
            ReporteFinancieroDAO dao = new ReporteFinancieroDAO(conexion);
            ReporteFinancieroService servicio = new ReporteFinancieroService(dao);

            // Crear Vista y mostrar menú
            ReporteFinancieroView vista = new ReporteFinancieroView(servicio);
            vista.mostrarMenu();

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            pausa();
        } finally {
            // Cerrar conexión
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                    System.out.println("✅ Conexión cerrada correctamente.");
                }
            } catch (Exception e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void pausa() {
        System.out.print("\nPresione Enter para continuar...");
        sc.nextLine();
    }
}