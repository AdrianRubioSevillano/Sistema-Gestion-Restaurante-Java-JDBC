# Gestión de Restaurante (Java + JDBC)

Este es un proyecto de backend que desarrollé para la asignatura de Sistemas de Bases de Datos II de mi carrera. El objetivo principal era crear un sistema para gestionar los menús, platos e ingredientes de un restaurante.

## Tecnologías que he usado
* **Java** (Lógica principal del programa)
* **SQL / JDBC** (Conexión directa a base de datos)
* **Maven** (Gestión de dependencias)

## Arquitectura del Proyecto
En lugar de hacer todo el programa en un par de archivos mezclando la interfaz gráfica con las consultas a la base de datos, decidí aplicar una arquitectura en capas reales:

* **Separación de responsabilidades (DAO):** Toda la comunicación con la base de datos está aislada en la capa Dao. El resto de la aplicación no sabe nada de bases de datos, solo le pide datos a esta capa.
* **Todo a mano (Sin Frameworks):** He usado JDBC puro con PreparedStatement. Quería entender bien cómo funcionan las conexiones, las transacciones y cómo evitar inyecciones SQL antes de usar frameworks.
* **Excepciones propias:** Me tomé el tiempo de crear excepciones personalizadas (por ejemplo, PrecioMenuIsRequiredAppException o NombrePlatoIsAlreadyExistAppException). Esto me permite validar los datos de forma muy estricta antes de que toquen la base de datos.

## ¿Qué hace el programa?
* Permite crear menús y asignarles un periodo de validez.
* Gestiona platos y calcula exactamente qué cantidad de cada ingrediente se necesita, evitando datos duplicados en las tablas.
