# InventarioAppG1

Aplicación JavaFX de inventario de equipos con integración JDBC (MySQL) y
navegación entre 3 escenas: **login**, **main** y **create**.

## Estructura

```
cesur.dam.inventarioappg1
├── AppG1                  → arranque: inicializa la BD y cede el control a la navegación
├── Launcher               → main()
├── controllers
│   ├── NavigationController → controlador de navegación: cambia la escena activa
│   ├── LoginController      → valida el login y navega a main
│   ├── MainController       → tabla con los equipos almacenados en la BD
│   └── CreateController     → alta de equipos (se guarda en BD si todo es correcto)
├── data                   → capa DAO reutilizada (JDBC)
│   ├── DAO                 → interfaz genérica
│   ├── EquipoJdbcDAO       → implementación JDBC sobre la tabla Equipo
│   ├── DataSourceFactory   → DataSource MySQL (singleton)
│   └── DataBaseInitializer → crea la tabla y carga datos iniciales
└── models
    └── Equipo             → modelo (id, nombre, categoria, disponible)
```

Vistas FXML en `src/main/resources/cesur/dam/inventarioappg1/views/`:
`login-view.fxml`, `main-view.fxml` y `create-view.fxml`.

## Base de datos

- MySQL en `jdbc:mysql://localhost:3309/ad` (misma configuración que `HelloJdbcG1`).
- Las credenciales se toman de las variables de entorno **USER** y **PASSWORD**.
- Al arrancar, la app crea la tabla `Equipo` si no existe (`init.sql`) y, si está
  vacía, carga los datos de ejemplo (`data.sql`).

## Ejecución

PowerShell (misma BD y credenciales que `HelloJdbcG1`):

```powershell
$env:USER = "USER"
$env:PASSWORD = "pwd"
.\mvnw.cmd javafx:run
```

También puedes crear una run configuration de IntelliJ con esas dos variables
de entorno y `cesur.dam.inventarioappg1.Launcher` como clase principal.

## Navegación

`NavigationController` mantiene el `Stage` principal. Con
`NavigationController.getInstance().go(View.MAIN)` se carga el FXML de la vista
de destino, se crea su escena y se instala en el escenario. Como cada
navegación recarga el FXML, la vista `main` vuelve a leer de la base de datos
en cada visita (no hace falta refrescar a mano tras crear un equipo).
