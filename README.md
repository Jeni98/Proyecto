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

#Funcionamiento

##Tablas
La aplicacion se conecta con una base de datos h2 por medio de 
dependencias de Spring Boot. En esta base de datos se crean 4 tablas:
Usuarios, Productos, Orden y Detalles (En esta ultima se guardan los
detalles de la orden).

En la siguiente imagen se muestran las relaciones entre tablas:

