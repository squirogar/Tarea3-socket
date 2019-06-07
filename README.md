# Tarea3-socket
------------------------------

    CHAT TAREA 3 REDES

------------------------------

1. Introduccion:
Esta aplicación consiste en una sala de chat comunitaria. Los usuarios se conectarán a un servidor que tiene una sala que reune a todos estos usuarios.

2. Cómo iniciar la aplicación:
- Cliente: Para ejecutar la aplicación de de cliente, se debe ingresar lo siguiente en la consola (cmd en Windows, terminal en Linux):

    javac ClienteChat.java
    
    java ClienteChat ip numero_puerto
    
en donde ip, corresponde a la dirección ip del servidor y el numero del puerto debe ser igual al numero de puerto del servidor para que funcione.

- Servidor: Para ejecutar el servicio de chat en la maquina servidor, se debe ingresar lo siguiente en la consola (cmd en Windows, terminal en Linux):

    javac ServidorChat.java
    
    java ServidorChat numero_puerto

El numero_puerto es el puerto donde se llevará a cabo la conexion

3. Cómo utilizar la aplicación:
- Cliente: 
Una vez iniciada, la aplicación le pedirá que ingrese su alias. Alias es el nombre o nickname que lo identificará en la sala de chat. Luego, usted podrá chatear con las personas que estén en la sala.
Si desea salir introduzca el comando /chat y la aplicación se terminará.

- Servidor:
Una vez iniciado el servicio, este escuchará las conexiones y las manejará, desde la pantalla de la consola usted podrá ver los mensajes que envén los distintos usuarios.
Si desea salir aprete Ctrl + c en Linux o Ctrl + z en Windows.

