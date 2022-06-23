<div id="top"></div>

<h3 align="center">Library App - Trabajo obligatorio del curso Arquitectura de Software en la práctica</h3>
</div>

### Sobre el proyecto

Este repositorio contiene el código de un SaaS para bibliotecas desarrollado, según la letra del obligatorio, para una ONG que desea fomentar la creación de bibliotecas barriales, con la finalidad de promover la cultura y facilitar el acceso a los libros, permitiéndoles a cada biblioteca gestionar su catálogo de libros y las reservas de los mismos. Administradores pueden crear su organizacion, y de manera independiente a las demas organizaciones manejar su stock de libros y hacer varias consultas.

### Alcance del proyecto

Se lograron hacer todas las features planeadas, estas siendo:

Features
======================
Feature | Descripción | Demo
--- | --- | ---
RF1 |  Registro de usuario administrador | <img src="https://user-images.githubusercontent.com/22801835/175170284-a0d51019-f319-4093-8014-4cb55640f0f0.gif" width="60%">
RF2 | Registro de usuarios mediante invitación | <img src="https://user-images.githubusercontent.com/22801835/175204201-406b186d-cb4b-46ca-8444-14df385a4aac.gif" width="60%">
RF3 | Autenticación de usuario | <img src="https://user-images.githubusercontent.com/22801835/175204673-f10cc8ea-f954-485f-8432-bf77ae5191b6.gif" width="60%">
RF4 | Gestión de clave de aplicación |  <img src="https://user-images.githubusercontent.com/22801835/175205768-0eaeafb4-7ab2-447a-aa97-866800bab5ed.gif" width="60%">
RFC5.1 | Alta de libros | <img src="https://user-images.githubusercontent.com/22801835/175205969-e498f85b-d36f-478f-9fac-9764398c32f2.gif" width="60%">
RFC5.2 | Modificación de libros | <img src="https://user-images.githubusercontent.com/22801835/175206758-99396345-da62-4c1c-8df3-3fd7296b9b7d.gif" width="60%">
RFC5.3 | Baja de libros | <img src="https://user-images.githubusercontent.com/22801835/175207115-ac756041-2f31-414a-b32c-4a7db297f589.gif" width="60%">
RF6 | Catálogo de libros | <img src="https://user-images.githubusercontent.com/22801835/175207451-40b75905-4727-492b-9d6b-b94d458151a0.gif" width="60%">
RF7 | Reserva de libros | <img src="https://user-images.githubusercontent.com/22801835/175207630-45cbb3bb-e393-442c-ac5d-314eea2db09d.gif" width="60%">
RF8 /RF15| Visualización de reservas activas / Visualizar estado de reservas | <img src="https://user-images.githubusercontent.com/22801835/175208275-cd84d1e4-5500-4d2b-b2a4-2588e0967da4.gif" width="60%">
RF11 | Escribir reseña | <img src="https://user-images.githubusercontent.com/22801835/175208434-025b60f9-e680-4df6-b737-40f9d7964fb1.gif" width="60%">
RF12 | Eliminar reseña | <img src="https://user-images.githubusercontent.com/22801835/175208594-133a750f-209e-4306-bf66-483f90e0fefb.gif" width="60%">
RF13 | Ver reseñas | <img src="https://user-images.githubusercontent.com/22801835/175209021-c234ea97-32ca-44c1-a9d3-0cbf352bbb52.gif" width="60%">
RF14 | Devolver libro | <img src="https://user-images.githubusercontent.com/22801835/175209519-83d37015-746c-4827-ac82-a0c6556530e8.gif" width="60%">

### Tecnologias usadas en frontend

 * Android: Fue sobre este sistema operativo que se tuvo que desarollar la aplicación.
 * Android Jetpack: Un suite de librerías que facilitan el desarollo en Android. Entre las que ofrece las mas usadas fueron LiveData, ViewModel, View Binding y el componente de Navegación.
 * Kotlin: Fue el lenguaje elegido para el desarrollo en Android, puede ser interoperable con Java pero se utilizó siempre solo Kotlin dado que el equipo así lo prefería, y transformar entre Java y Kotlin era una funcionalidad dada por Android Studio.
 * Retrofit: Librería utilizada para interactuar con el la API REST que define el backend.
 * Koin: Framework de inyección de dependencias.

### Tecnologias usadas en backend
* Node.js: Se utilizó en la mayoría de los microservicios
* C\#: Se utilizó para el microservicio de reviews
* Docker
