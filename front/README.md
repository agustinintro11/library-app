<div id="top"></div>

<h3 align="center">Library app 211150-211064-186499 Frontend</h3>
</div>

### Sobre el proyecto

Este repositorio contiene el código del frontend de un SaaS para bibliotecas desarrollado para una conocida ONG que desea fomentar la creación de bibliotecas barriales, con la finalidad de promover la cultura y facilitar el acceso a los libros, permitiéndoles a cada biblioteca gestionar su catálogo de libros y las reservas de los mismos. Administradores pueden crear su organizacion, y de manera independiente a las demas organizaciones manejar su stock de libros y hacer varias consultas.

### Alcance del proyecto

Para el frontend se lograron hacer todas las features planeadas, estas siendo:

- RF1. Registro de usuario administrador

- RF2. Registro de usuarios mediante invitación

- RF3. Autenticación de usuario

- RF4. Gestión de clave de aplicación

- RF5. CRUD de libros
    - RFC5.1 Alta de libros
    - RFC5.2 Modificación de libros
    - RFC5.3 Baja de libros

- RF6. Catálogo de libros

- RF7. Reserva de libros

- RF8. Visualización de reservas activas

- RF11. Escribir reseña

- RF12. Eliminar reseña

- RF13. Ver reseñas

- RF14. Devolver libro

- RF15. Visualizar estado de reservas

### Tenologias usadas

 * Android: Fue sobre este sistema operativo que se tuvo que desarollar la aplicación. En todo momento se trató de respetar los lifecycles en los fragments (y en la activity) de manera de comportarse correctamente ante cualquier decisión del sistema operativo.  
 * Android Jetpack: Un suite de librerías que facilitan el desarollo en Android. Entre las que ofrece las mas usadas fueron LiveData, ViewModel, View Binding y el componente de Navegación.
 * Kotlin: Fue el lenguaje elegido para el desarrollo en Android, puede ser interoperable con Java pero se utilizó siempre solo Kotlin dado que el equipo así lo prefería, y transformar entre Java y Kotlin era una funcionalidad dada por Android Studio.
 * Retrofit: Librería utilizada para interactuar con el la API REST que define el backend.
 * Koin: Framework de inyección de dependencias.
