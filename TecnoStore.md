# 🏪 TecnoStore - Sistema de Gestión de Ventas

## 📸 Diagramas Visuales

### Diagrama de Arquitectura
![Arquitectura del Sistema](img/Untitled%20diagram-2026-02-11-040854.png)

### Modelo de Datos - Diagrama Entidad-Relación (ER)
![Modelo de Datos](img/drawSQL-image-export-2026-02-11%20(2).png)

## Tabla de Contenidos

1. [Diagramas Visuales](#-diagramas-visuales)
2. [Descripción Visual](#-descripción-visual)
3. [Tablas de la Base de Datos](#-tablas-de-la-base-de-datos)
4. [Relaciones entre Tablas](#-relaciones-entre-tablas)
5. [Casos de Uso Principales](#-casos-de-uso-principales)
6. [Reportes Disponibles](#-reportes-disponibles)
7. [Validaciones Implementadas](#-validaciones-implementadas)
8. [Patrones de Diseño](#-patrones-de-diseño)
9. [Estadísticas del Proyecto](#-estadísticas-del-proyecto)
10. [Flujo Completo de Ejecución](#-flujo-completo-de-ejecución)
11. [Checklist de Funcionalidades](#-checklist-de-funcionalidades)

---

## 🎨 Descripción Visual

### Flujo de Operaciones

```
┌─────────────────────────────────────────────────────────────┐
│                    INTERFAZ USUARIO (MENÚ)                  │
│                      (Vista/Console)                        │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────┼──────────────┐
        │              │              │
   ┌────▼─────┐  ┌────▼─────┐  ┌────▼──────┐
   │ Gestionar │  │ Gestionar │  │ Realizar  │
   │Celulares  │  │ Clientes  │  │ Ventas    │
   └────┬─────┘  └────┬─────┘  └────┬──────┘
        │              │              │
┌───────┴──────────────┴──────────────┴───────┐
│         CONTROLADORES (Lógica Negocio)      │
│  - GestorCelulares                          │
│  - GestorClientes                           │
│  - GestorVentas                             │
│  - Validador                                │
│  - ReporteUtils                             │
└───────┬───────────────────────────────────┬─┘
        │                                   │
    ┌───▼────────────────────────────────┐ │
    │  CAPA DE PERSISTENCIA (DAO)        │ │
    │  - CelularDAO                      │ │
    │  - ClienteDAO                      │ │
    │  - VentaDAO                        │ │
    │  - ConexionDB                      │ │
    └───┬────────────────────────────────┘ │
        │                                   │
        └──────────────────┬────────────────┘
                           │
                 ┌─────────▼─────────┐
                 │  MySQL Database   │
                 │  tecnostore_db    │
                 │  - celulares      │
                 │  - clientes       │
                 │  - ventas         │
                 │  - detalle_ventas │
                 │  - marca          │
                 │  - gama           │
                 └───────────────────┘
```

---

## 📋 Tablas de la Base de Datos
- ✅ **Testabilidad:** Cada capa puede testearse por separado

---

## Diseño del Modelo de Datos

### 📊 Tablas Creadas

#### **1. Tabla: celulares**
```sql
CREATE TABLE celulares (
  id INT AUTO_INCREMENT PRIMARY KEY,
  marca VARCHAR(50) NOT NULL,
  modelo VARCHAR(50) NOT NULL,
  precio DECIMAL(10, 2) NOT NULL,
  stock INT NOT NULL,
  sistema_operativo VARCHAR(50) NOT NULL,
  gama VARCHAR(20) NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

**Propósito:** Almacenar el catálogo de productos.

**Campos:**
- `id`: Identificador único (clave primaria, auto-incremento)
- `marca`: Fabricante (Samsung, Apple, Xiaomi, etc.)
- `modelo`: Nombre del dispositivo
- `precio`: Costo unitario (DECIMAL para precisión monetaria)
- `stock`: Cantidad disponible
- `sistema_operativo`: iOS, Android, etc.
- `gama`: Clasificación (Alta, Media, Baja)

**Justificación:**
- `DECIMAL(10, 2)`: Precisión monetaria (8 dígitos enteros, 2 decimales)
- `InnoDB`: Soporte de transacciones
- `UTF8MB4`: Soporte de caracteres especiales

---

#### **2. Tabla: clientes**
```sql
CREATE TABLE clientes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  identificacion VARCHAR(20) UNIQUE NOT NULL,
  correo VARCHAR(100) NOT NULL,
  telefono VARCHAR(15) NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

**Propósito:** Registro de clientes.

**Campos:**
- `id`: Identificador único
- `nombre`: Nombre completo
- `identificacion`: Cédula/Pasaporte (UNIQUE para evitar duplicados)
- `correo`: Email del cliente
- `telefono`: Número de contacto
- `fecha_creacion`: Timestamp de creación

**Justificación:**
- `UNIQUE` en identificación: Previene duplicados de clientes
- Permite búsquedas rápidas por identificación

---

#### **3. Tabla: ventas**
```sql
CREATE TABLE ventas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente INT NOT NULL,
  fecha DATE NOT NULL,
  total DECIMAL(10, 2) NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

**Propósito:** Registro de transacciones de venta.

**Campos:**
- `id`: Identificador de venta
- `id_cliente`: Referencia al cliente (FK)
- `fecha`: Fecha de la venta
- `total`: Monto total (con IVA incluido)
- `fecha_creacion`: Timestamp

**Justificación:**
- `FOREIGN KEY ... ON DELETE CASCADE`: Si se elimina un cliente, sus ventas también se eliminan (integridad referencial)
- `DATE`: Más eficiente que TIMESTAMP para comparaciones de fechas

---

#### **4. Tabla: detalle_ventas**
```sql
CREATE TABLE detalle_ventas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_venta INT NOT NULL,
  id_celular INT NOT NULL,
  cantidad INT NOT NULL,
  subtotal DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (id_venta) REFERENCES ventas(id) ON DELETE CASCADE,
  FOREIGN KEY (id_celular) REFERENCES celulares(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

**Propósito:** Desglose de productos en cada venta (relación muchos-a-muchos).

**Campos:**
- `id`: Identificador del detalle
- `id_venta`: Referencia a venta (FK)
- `id_celular`: Referencia a celular (FK)
- `cantidad`: Unidades vendidas
- `subtotal`: Precio unitario × cantidad (sin IVA)

**Justificación:**
- Normalización: Evita duplicar datos de celulares en tabla ventas
- Permite ventas multi-producto

---

#### **5. Tabla: marca** y **6. Tabla: gama**

```sql
CREATE TABLE marca (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(200),
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE gama (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL UNIQUE,
  descripcion VARCHAR(200),
  factor_precio DECIMAL(3, 2) DEFAULT 1.0,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

**Propósito:** Maestros de datos para normalización.

**Nota:** En la implementación actual, estos se almacenan como strings en celulares, pero podrían normalizarse con FKs.

---

### 📐 Relaciones

```
clientes (1) ──────→ (N) ventas
                         ↓
                         │ (1)
                         │
                    (N) detalle_ventas ←──────→ (1) celulares
```

---

## Arquitectura del Sistema

### 🏛️ Capas Implementadas

```
┌────────────────────────────────────┐
│    VISTA (Menu.java)               │  - Interface de usuario (consola)
│  - Menús interactivos              │  - Entrada/salida
│  - 518 líneas de código            │
└────────────┬───────────────────────┘
             │ Llama
┌────────────▼───────────────────────┐
│    CONTROLADOR (Gestor*)           │  - Lógica de negocio
│  - GestorCelulares.java            │  - Validaciones
│  - GestorClientes.java             │  - Orquestación
│  - GestorVentas.java               │
└────────────┬───────────────────────┘
             │ Utiliza
┌────────────▼───────────────────────┐
│    MODELO (Entidades)              │  - POJOs (Plain Old Java Objects)
│  - Celular.java                    │  - Getters/Setters
│  - Cliente.java                    │  - toString()
│  - Venta.java                      │
│  - DetalleVenta.java               │
└────────────┬───────────────────────┘
             │ Utiliza
┌────────────▼───────────────────────┐
│    PERSISTENCIA (DAO)              │  - Acceso a datos
│  - ConexionDB.java                 │  - Operaciones CRUD
│  - CelularDAO.java                 │  - PreparedStatements
│  - ClienteDAO.java                 │
│  - VentaDAO.java                   │
└────────────┬───────────────────────┘
             │ Abre/cierra
┌────────────▼───────────────────────┐
│    MySQL (tecnostore_db)           │  - Persistencia permanente
└────────────────────────────────────┘
```

### 📦 Clases Principales

#### **Modelo**

| Clase | Responsabilidad | Atributos |
|-------|-----------------|-----------|
| `Celular` | Representa un dispositivo | id, marca, modelo, precio, stock, SO, gama |
| `Cliente` | Datos del comprador | id, nombre, identificacion, correo, telefono |
| `Venta` | Transacción | id, idCliente, fecha, total, detalles (List) |
| `DetalleVenta` | Línea de venta | id, idVenta, idCelular, cantidad, subtotal |

**Características:**
- Getters/Setters para encapsulamiento
- Constructores sobrecargados (con/sin ID)
- `toString()` para debugging
- Uso de `LocalDate` para fechas

---

#### **Controlador**

| Clase | Responsabilidad |
|-------|-----------------|
| `GestorCelulares` | CRUD de celulares, filtrados por gama, stock bajo |
| `GestorClientes` | CRUD de clientes, búsqueda por ID, validaciones |
| `GestorVentas` | Registro de ventas, cálculo de IVA, actualización de stock |

**Métodos clave en GestorVentas:**
```java
registrarVenta(int idCliente, List<DetalleVenta> detalles)
  - Calcula subtotal
  - Aplica IVA 19%
  - Inserta venta
  - Inserta detalles
  - Decrementa stock automáticamente
```

---

#### **Persistencia (DAO)**

| Clase | Métodos |
|-------|---------|
| `ConexionDB` | `obtenerConexion()`, `cerrarConexion(conn)` |
| `CelularDAO` | `guardar()`, `actualizar()`, `eliminar()`, `obtenerPorId()`, `obtenerTodos()` |
| `ClienteDAO` | CRUD completo + `obtenerPorIdentificacion()` |
| `VentaDAO` | `guardar()`, `guardarDetalle()`, `obtenerTodas()`, `obtenerPorMes()` |

**Patrón de Conexión:**
```java
try (Connection conn = ConexionDB.obtenerConexion();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
    // Usar stmt
} // auto-close de recursos
```

---

#### **Vista**

**Menu.java (518 líneas)**
- Menú principal con 5 opciones
- Submenús para cada módulo
- Validación de entrada
- Manejo de excepciones
- UX amigable (símbolos ✓, ✗)

---

#### **Utilidades**

**Validador.java**
```
✓ validarCorreo(String) → Regex
✓ validarIdentificacion(String) → Longitud 8-20
✓ validarPrecio(double) → > 0
✓ validarStock(int) → >= 0
✓ validarNombre(String) → Longitud 3-100
✓ validarTelefono(String) → 10+ dígitos
✓ validarGama(String) → "Baja"|"Media"|"Alta"
✓ validarOpcion(int, min, max) → Rango
```

**ReporteUtils.java**
```
✓ generarReporteVentas() → Archivo .txt
✓ mostrarStockBajo() → Consola
```

---

## Validaciones Implementadas

### 🔐 Validaciones en Campo

| Campo | Validación | Dónde |
|-------|-----------|-------|
| **Correo** | Regex: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$` | GestorClientes, Validador |
| **Identificación** | Longitud 8-20, UNIQUE en BD | GestorClientes |
| **Precio** | > 0 | GestorCelulares |
| **Stock** | >= 0 | GestorCelulares |
| **Nombre** | 3-100 caracteres | Validador |
| **Teléfono** | 10+ dígitos, solo números | Validador |
| **Gama** | "Baja", "Media", "Alta" | Validador |

### 🔍 Validaciones en Negocio

| Escenario | Validación |
|-----------|-----------|
| **Duplicados de cliente** | `obtenerPorIdentificacion()` verifica existencia |
| **Stock insuficiente** | `GestorVentas` valida antes de vender |
| **Venta sin productos** | Lista vacía rechazada |
| **Cliente no existe** | Excepción si idCliente inválido |

---

## Índices de Base de Datos

### Índices Implementados

```sql
-- Índice en clave primaria (automático)
ALTER TABLE celulares ADD INDEX idx_marca (marca);
ALTER TABLE celulares ADD INDEX idx_gama (gama);

-- Para búsquedas frecuentes
ALTER TABLE clientes ADD INDEX idx_identificacion (identificacion);

-- Para reportes por mes/año
ALTER TABLE ventas ADD INDEX idx_fecha (fecha);
ALTER TABLE ventas ADD INDEX idx_cliente (id_cliente);

-- Para relación detalle
ALTER TABLE detalle_ventas ADD INDEX idx_venta (id_venta);
ALTER TABLE detalle_ventas ADD INDEX idx_celular (id_celular);
```

### Justificación

- **idx_identificacion:** Búsqueda rápida de cliente por ID (O(log N) vs O(N))
- **idx_fecha:** Filtrado rápido para reportes mensuales
- **idx_marca, idx_gama:** Filtrado de productos
- **idx_venta, idx_celular:** Búsquedas en detalles de venta

---

## Patrones de Diseño

### 1. **MVC (Model-View-Controller)**
```
Menu (View) → GestorCelulares (Controller) → CelularDAO (Model)
```

### 2. **DAO (Data Access Object)**
```java
public interface ICelularDAO {
    void guardar(Celular c) throws SQLException;
    Celular obtenerPorId(int id) throws SQLException;
    List<Celular> obtenerTodos() throws SQLException;
}
```

### 3. **Singleton (Conexión)**
```java
public class ConexionDB {
    // Carga dinámica del driver (una sola vez)
    static {
        try {
            Class.forName(DRIVER); // Se ejecuta una sola vez
        } catch (ClassNotFoundException e) { ... }
    }
}
```

### 4. **Builder Pattern (Implícito)**
```java
// Constructor sobrecargado para flexibilidad
Celular celular = new Celular(marca, modelo, precio, stock, so, gama);
Celular celularConId = new Celular(id, marca, modelo, precio, stock, so, gama);
```

### 5. **Try-With-Resources**
```java
try (Connection conn = ConexionDB.obtenerConexion();
     PreparedStatement stmt = conn.prepareStatement(sql)) {
    // Auto-close de Connection y PreparedStatement
}
```

---

## Explicación de Agregaciones

### 1. Stock Bajo

```java
public List<Celular> obtenerStockBajo() throws SQLException {
    return dao.obtenerTodos().stream()
            .filter(c -> c.getStock() < 5)
            .collect(Collectors.toList());
}
```

**Stream API:**
- `.filter()`: Filtra productos con stock < 5
- `.collect()`: Convierte a List

**Complejidad:** O(n)

---

### 2. Ventas por Mes

```java
public List<Venta> obtenerVentasMes(int mes, int anio) throws SQLException {
    return dao.obtenerPorMes(mes, anio);
}

// SQL:
// SELECT * FROM ventas WHERE MONTH(fecha)=? AND YEAR(fecha)=?
```

**Beneficio:** Usa funciones SQL para no cargar todo en memoria

---

### 3. Total de Ventas

```java
public double obtenerTotalVentasMes(int mes, int anio) throws SQLException {
    return obtenerVentasMes(mes, anio).stream()
            .mapToDouble(Venta::getTotal)
            .sum();
}
```

**Stream API:**
- `.mapToDouble()`: Convierte a DoubleStream
- `.sum()`: Suma todos los valores

---

## Flujo de Datos

### Ejemplo: Registrar Venta

```
1. Menu.menuVentas()
   └─ Selecciona cliente por ID
   └─ Agrega celulares al carrito (validación de stock)
   
2. GestorVentas.registrarVenta(idCliente, detalles)
   └─ Calcula subtotal
   └─ Aplica IVA 19%
   └─ VentaDAO.guardar(venta) → retorna idVenta
   
3. Para cada detalle:
   └─ VentaDAO.guardarDetalle(detalle)
   └─ GestorCelulares.actualizar() → decrementa stock
   └─ CelularDAO.actualizar(celular)
   
4. BD: inserta en ventas, detalle_ventas, actualiza celulares.stock
   
5. Menu: muestra recibo con totales
```

### Tabla de Responsabilidades

| Responsabilidad | Quién | Cómo |
|-----------------|-------|------|
| Validar email | Validador | Regex |
| Prevenir duplicados | ClienteDAO | UNIQUE constraint |
| Calcular IVA | GestorVentas | `subtotal * 1.19` |
| Actualizar stock | GestorVentas + CelularDAO | UPDATE |
| Persistir | DAO | JDBC + PreparedStatement |
| Mostrar resultado | Menu | System.out.println |

---

## Mejoras Posibles

### 🔧 Mejoras en Arquitectura

#### 1. Pasar a ORM (Hibernate/JPA)
**Ventaja:** Menos código JDBC, validaciones automáticas
```java
@Entity
@Table(name = "celulares")
public class Celular {
    @Id @GeneratedValue
    private int id;
    
    @Column(nullable = false)
    private String marca;
    // ...
}
```

#### 2. Inyección de Dependencias (Spring)
**Ventaja:** Desacoplamiento de clases
```java
@Service
public class GestorCelulares {
    @Autowired
    private CelularDAO dao;
    // ...
}
```

#### 3. API REST (Spring Boot)
**Ventaja:** Integración con frontend web/móvil
```java
@RestController
@RequestMapping("/api/celulares")
public class CelularController {
    @GetMapping("/{id}")
    public Celular obtener(@PathVariable int id) { ... }
}
```

---

### 🔒 Mejoras en Seguridad

#### 1. Externalizar Credenciales
**Actual (❌ Inseguro):**
```java
private static final String USUARIO = "campus2023";
private static final String PASSWORD = "campus2023";
```

**Mejorado (✅ Seguro):**
```java
// application.properties
db.username=${DB_USER}
db.password=${DB_PASS}
```

#### 2. Encriptar Contraseñas de Clientes
```java
String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
```

#### 3. Usar SSL/TLS en Conexión MySQL
```java
jdbc:mysql://localhost:3306/tecnostore_db?useSSL=true&serverTimezone=UTC
```

---

### 📊 Mejoras en Datos

#### 1. Tabla de Auditoría
```sql
CREATE TABLE auditoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tabla VARCHAR(50),
    accion VARCHAR(20),
    id_registro INT,
    usuario VARCHAR(50),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 2. Historial de Precios
```sql
CREATE TABLE historial_precios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_celular INT,
    precio_anterior DECIMAL(10,2),
    precio_nuevo DECIMAL(10,2),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_celular) REFERENCES celulares(id)
);
```

#### 3. Control de Descuentos
```sql
CREATE TABLE promociones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE,
    descuento_porcentaje DECIMAL(5,2),
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('activo', 'inactivo')
);
```

---

### 🧪 Mejoras en Testing

#### 1. Tests Unitarios (JUnit)
```java
@Test
public void testValidarCorreoValido() {
    assertTrue(Validador.validarCorreo("test@example.com"));
}

@Test
public void testValidarCorreoInvalido() {
    assertFalse(Validador.validarCorreo("invalid-email"));
}
```

#### 2. Tests de Integración
```java
@Test
public void testRegistrarCelularEnBD() throws SQLException {
    gestorCelulares.registrar("Samsung", "S23", 899.99, 10, "Android", "Alta");
    Celular c = gestorCelulares.listar().get(0);
    assertEquals("Samsung", c.getMarca());
}
```

#### 3. Mock Objects (Mockito)
```java
@Mock
private CelularDAO mockDAO;

@Test
public void testObtenerStock() throws SQLException {
    when(mockDAO.obtenerTodos()).thenReturn(celularesMock);
    // ...
}
```

---

### 📈 Mejoras en Reportes

#### 1. Reporte en PDF
```java
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

Document doc = new Document(new PdfDocument(new PdfWriter("reporte.pdf")));
doc.add(new Paragraph("Ventas TecnoStore"));
```

#### 2. Reporte en Excel
```java
import org.apache.poi.ss.usermodel.*;

Workbook wb = new XSSFWorkbook();
Sheet sheet = wb.createSheet("Ventas");
Row row = sheet.createRow(0);
```

#### 3. Visualización de Datos
```java
// Con JFreeChart
DefaultCategoryDataset dataset = new DefaultCategoryDataset();
dataset.setValue(100, "Ventas", "Enero");
JFreeChart chart = ChartFactory.createBarChart(...);
```

---

### 🚀 Mejoras en Rendimiento

#### 1. Paginación en Listados
```java
public List<Celular> listarPaginado(int pagina, int tamanio) throws SQLException {
    String sql = "SELECT * FROM celulares LIMIT ? OFFSET ?";
    // ...
}
```

#### 2. Connection Pool (HikariCP)
```java
HikariConfig config = new HikariConfig();
config.setJdbcUrl("jdbc:mysql://localhost:3306/tecnostore_db");
config.setUsername("campus2023");
config.setPassword("campus2023");
config.setMaximumPoolSize(10);

HikariDataSource ds = new HikariDataSource(config);
Connection conn = ds.getConnection();
```

#### 3. Logging (Log4j)
```java
private static final Logger logger = Logger.getLogger(GestorVentas.class);

logger.info("Venta registrada: ID=" + idVenta);
logger.error("Error al actualizar stock", e);
```

---

### 🎯 Mejoras en UX

#### 1. Interfaz Gráfica (Swing/JavaFX)
```java
public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        JButton btnVender = new JButton("Vender");
        btnVender.addActionListener(e -> mostrarDialogoVenta());
    }
}
```

#### 2. Búsqueda en Tiempo Real
```java
@Override
public void insertUpdate(DocumentEvent e) {
    String filtro = textoBusqueda.getText();
    List<Celular> resultados = celulares.stream()
            .filter(c -> c.getMarca().contains(filtro))
            .collect(Collectors.toList());
    actualizarTabla(resultados);
}
```

---

## Conclusiones

El sistema TecnoStore implementa una solución robusta y escalable para la gestión de ventas de celulares. La arquitectura MVC con patrón DAO garantiza mantenibilidad, las validaciones SOLID aseguran integridad de datos, y el uso de JDBC proporciona control fino sobre la persistencia.

**Fortalezas:**
- ✅ Arquitectura clara y modular
- ✅ Validaciones exhaustivas
- ✅ Uso eficiente de Stream API
- ✅ Manejo seguro de recursos (try-with-resources)
- ✅ Integridad referencial en BD

**Áreas de mejora:**
- 🔧 Migrar a ORM (Hibernate)
- 🔧 Implementar Spring Boot
- 🔧 Externalizar configuración
- 🔧 Agregar tests unitarios
- 🔧 Implementar logging
- 🔧 Crear API REST

---

**Autor:** Stiven Martínez Villamizar  
**Versión:** 1.0  
**Fecha:** 10 de febrero de 2026  
**Estado:** Funcional con mejoras posibles
