# Proyecto
Se crea una aplicación para la venta de productos "Cesta Dorada".
Alli podras encontrar funciones solamente para administrador y 
funciones solamente para usuarios.

Para entrar como administrador utiliza las siguientes credenciales:
correo: SDEVE@HOTMAIL.COM
contraseña: 123ALE

Para acceder como usuario podras loguearte en la aplicación. Esto es 
porque solamente se creo el fomulario para que la persona que se registre
quede con un rol de usuario.

# Funcionamiento

## Tablas
La aplicacion se conecta con una base de datos h2 por medio de 
dependencias de Spring Boot. En esta base de datos se crean 4 tablas:
Usuarios, Productos, Orden y Detalles (En esta ultima se guardan los
detalles de la orden).

En la siguiente imagen se muestran las relaciones entre tablas:

![Relacion entre tablas](https://github.com/Jeni98/Imagenes/blob/06f41d7ad5d96505fe60bd7d5c7a7a161ccc3824/tablas.png)
De la ilustración 1. Podemos decir las siguientes relaciones, las cuales 
se crean en el paquete denominado model del proyecto:

•	La tabla Usuario tiene una relación de uno a muchos con la tabla Orden y 
con la tabla Productos. 
•	La tabla Orden tiene una relación de muchos a uno con la tabla Usuarios 
y de uno a muchos con la tabla Detalles.
•	La tabla Productos tiene una relación de muchos a uno con la tabla Usuarios.
•	La tabla Detalles tiene una relación de muchos a uno con la tabla Orden y 
con la tabla Productos.

## ROLES

•	ADMIN
La persona que tenga el rol de ADMIN tiene la funcionalidad de ver los usuarios 
que se han registro. De igual forma podrá ver las ordenes y los detalles de estas
que están relacionados a cada usuario. 

Adicionalmente el administrador podrá crear un nuevo producto, modificarlo y 
eliminarlo de la base de datos. También puede cambiar la imagen de este o agregarla.

•	USER
La persona que acceda a la aplicación con el rol de user podrá ver solamente la lista 
de productos y ver los detalles de cada uno. También tiene la opción de agregar este 
producto al carrito y podrá escoger la cantidad que necesite con un máximo de 5 productos. 
En la parte de Orden podrá ver el total de lo que lleva hasta ahora por comprar. Al darle 
click al botón “ver orden”, mostrara los datos del usuario y los productos que se han 
añadido al carrito y allí podrá generar su orden si todos los datos están correctos.

## SEGURIDAD
La seguridad de la aplicación se realiza con la dependencia Spring Security de Spring Boot. 
Se realiza de tal forma que la persona que ingrese a la aplicación con rol de “USER”, no 
podra acceder a las funciones que tiene un administrador. Como lo son la creacion de un 
producto o ver los usuarios que se han registrado. Adicionalmente se realiza la encriptacion 
de la contraseña. 

