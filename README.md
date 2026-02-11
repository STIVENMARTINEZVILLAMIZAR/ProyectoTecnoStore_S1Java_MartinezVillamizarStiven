# Sistema de Gestión de Ventas de Celulares - TecnoStore

<img src="https://img.shields.io/badge/Java-17-orange?logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL-8.0.45-blue?logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/Maven-4.0.0-C71A36?logo=apache-maven&logoColor=white"/>
<img src="https://img.shields.io/badge/Editor-VSCode-007ACC?logo=visualstudiocode&logoColor=white"/>



## 🎯 Introducción al Proyecto

**TecnoStore** es un sistema integral de gestión de inventario y ventas para una tienda minorista de teléfonos celulares. El proyecto automatiza el control de catálogo de productos, base de datos de clientes, registro de transacciones y generación de reportes analíticos. Está desarrollado en **Java** con arquitectura **MVC**, utilizando **JDBC** para persistencia en **MySQL** y patrones de diseño para garantizar escalabilidad y mantenibilidad.

El sistema resuelve la necesidad de TecnoStore de migrar desde hojas de cálculo manuales a una solución informatizada, permitiendo:
- ✅ Gestión centralizada de celulares y su inventario
- ✅ Administración de clientes con validaciones
- ✅ Registro de ventas con cálculo automático de IVA (19%)
- ✅ Reportes y análisis en tiempo real
- ✅ Persistencia segura en base de datos MySQL

---

## 🛠️ Tecnologías Utilizadas

| Componente | Tecnología | Versión |
|-----------|-----------|---------|
| **Lenguaje** | Java | 17 |
| **Framework de Construcción** | Maven | 4.0.0 |
| **Base de Datos** | MySQL | 8.0.45 |
| **Driver JDBC** | MySQL Connector/J | 8.0.33 |
| **Testing** | JUnit | 4.13.2 |
| **Patrón Arquitectónico** | MVC (Model-View-Controller) | - |
| **API de Streams** | Java Streams API | Java 8+ |

---

## 📦 Características Principales

### 1️⃣ Gestión de Celulares
- Registrar, actualizar, eliminar y listar celulares
- Control de inventario con alertas de stock bajo (<5 unidades)
- Clasificación por gama (Alta, Media, Baja)
- Validación de precios y stock

### 2️⃣ Gestión de Clientes
- Registro con identificación única
- Validación de correo electrónico (regex)
- Búsqueda por identificación
- Perfil completo (nombre, teléfono, correo)

### 3️⃣ Gestión de Ventas
- Venta multi-producto (carrito de compras)
- Cálculo automático de IVA del 19%
- Actualización automática de stock
- Validación de disponibilidad

### 4️⃣ Reportes y Análisis
- Stock bajo por producto
- Ventas totales por mes/año
- Generación de reporte en archivo `.txt`
- Uso de **Stream API** para procesamiento de datos

---

## 🏗️ Estructura del Proyecto

```
proyectotecnostore_s1java_martinezvillamizarstiven/
├── src/main/java/com/mycompany/proyectotecnostore_s1java_martinezvillamizarstiven/
│   ├── Main.java                          # Punto de entrada
│   ├── modelo/                            # Entidades del negocio
│   │   ├── Celular.java
│   │   ├── Cliente.java
│   │   ├── Venta.java
│   │   └── DetalleVenta.java
│   ├── persistencia/                      # Acceso a datos (DAO)
│   │   ├── ConexionDB.java               # Gestor de conexiones
│   │   ├── CelularDAO.java
│   │   ├── ClienteDAO.java
│   │   └── VentaDAO.java
│   ├── controlador/                       # Lógica de negocio
│   │   ├── GestorCelulares.java
│   │   ├── GestorClientes.java
│   │   └── GestorVentas.java
│   ├── vista/                             # Interfaz de usuario
│   │   └── Menu.java                      # Menú interactivo en consola
│   └── utilidades/                        # Funciones auxiliares
│       ├── Validador.java                 # Validaciones
│       └── ReporteUtils.java              # Generación de reportes
├── pom.xml                                # Dependencias Maven
├── Bd_TecnoStore_Db.sql                  # Script DDL (crear tablas)
├── Consultas.sql                          # Script DML (datos iniciales)
└── README.md                              # Este archivo
```

---

## 🗄️ Base de Datos MySQL

**Nombre:** `tecnostore_db`

**Tablas:**
- `celulares` - Inventario de productos
- `clientes` - Registro de compradores
- `ventas` - Historial de transacciones
- `detalle_ventas` - Desglose de productos por venta
- `marca` - Fabricantes disponibles
- `gama` - Categorías de productos

**Credenciales Predeterminadas:**
```
Usuario: campus2023
Contraseña: campus2023
Host: localhost:3306
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java JDK 17+
- MySQL Server 8.0+
- Maven 4.0.0+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/usuario/ProyectoTecnoStore.git
   cd ProyectoTecnoStore_S1Java_MartinezVillamizarStiven
   ```

2. **Crear base de datos**
   ```bash
   mysql -u root -p < Bd_TecnoStore_Db.sql
   mysql -u root -p tecnostore_db < Consultas.sql
   ```

3. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn exec:java -Dexec.mainClass="com.mycompany.proyectotecnostore_s1java_martinezvillamizarstiven.Main"
   ```

---

## 📋 Ejemplo de Ejecución

```
========== TECNOSTORE - SISTEMA DE GESTIÓN ==========
1. Gestionar Celulares
2. Gestionar Clientes
3. Realizar Venta
4. Reportes
5. Salir
Opción: 1

========== GESTIÓN DE CELULARES ==========
1. Registrar celular
2. Listar celulares
3. Actualizar celular
4. Eliminar celular
5. Volver
Opción: 2

========== LISTADO DE CELULARES ==========
1 | Samsung Galaxy S23 | $899.99 | Stock: 15 | Alta
2 | Xiaomi Redmi Note 12 | $299.99 | Stock: 2 | Media
3 | Apple iPhone 15 | $1299.99 | Stock: 0 | Alta
```

---

## 📊 Validaciones Implementadas

| Campo | Validación |
|-------|-----------|
| **Correo** | Regex: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$` |
| **Identificación** | Longitud 8-20 caracteres, únicos |
| **Precio** | Mayor a 0 |
| **Stock** | Mayor o igual a 0 |
| **Nombre** | Longitud 3-100 caracteres |
| **Teléfono** | Mínimo 10 dígitos, solo números |
| **Gama** | Valores: "Baja", "Media", "Alta" |

---

## 🔒 Patrones de Diseño Implementados

- **MVC (Model-View-Controller):** Separación de responsabilidades
- **DAO (Data Access Object):** Abstracción de persistencia
- **Singleton:** Conexión única a base de datos
- **Builder Pattern (implícito):** En constructores sobrecargados

---

## 📁 Archivos Generados

- `reporte_ventas.txt` - Reporte general de todas las ventas (generado automáticamente)

---

## 👨‍💻 Autor

**Stiven Martínez Villamizar** - @STIVENMARTINEZVILLAMZAR

---

## 📚 Documentación Completa

Para detalles técnicos, diseño de base de datos, validaciones, índices y mejoras posibles, consultar [TecnoStore.md](./TecnoStore.md)

---

## 📝 Licencia

Este proyecto es de uso académico dentro del programa CampusLands.