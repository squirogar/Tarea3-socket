import java.net.*;
import java.io.*;


public class ServidorChat {
	public static void main(String[]args) {
		ServerSocket socketServidor = null;
		//Creacion de un socket para el servidor
		try {
			socketServidor = new ServerSocket(4444);
			System.out.println("Se creo socket del servidor en el puerto 4444");
		} catch (IOException e) {
			System.out.println("Error, el puerto 4444 está siendo ocupado por otra"
				+ "aplicacion");
			//sale si hay un error
			System.exit(1);
		}

		System.out.println("Servidor listo para aceptar requerimiento de "
			+ "clientes...");
			
		Socket socketCliente = null;
		Mensaje mensaje = new Mensaje();
		//El servidor se queda eternamente escuchando peticiones
		
		while(true) {
			try {
				System.out.println("Escuchando peticiones...");
				//acepta peticion y devulve un socket cliente
				socketCliente = socketServidor.accept(); 
				System.out.println("Se acepto al cliente: " 
					+ socketCliente.getLocalAddress());
				ChatHilo ch = new ChatHilo(socketCliente, mensaje);
				ch.start();
				
			} catch(IOException ioe) {
				System.out.println("Ha ocurrido un error al establecer conexion con un "
					+ " cliente!");
				ioe.printStackTrace();
			}
		}
		/*
			falta cerrar socket servidor
		*/ 
		
	}



}
