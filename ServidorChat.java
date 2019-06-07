import java.net.*;
import java.io.*;


public class ServidorChat {

	private static ServerSocket socketServidor;

	public static void main(String[]args) {
		
		//Creacion de un socket para el servidor
		int puerto = compruebaPuerto(args);
		try {
			socketServidor = new ServerSocket(puerto);
			System.out.println("Se creo socket del servidor en el puerto " + puerto);
		} catch (IOException e) {
			System.out.println("Error, el puerto " + puerto + " está siendo ocupado por otra"
				+ "aplicacion");
			//sale si hay un error
			System.exit(1);
		}

		System.out.println("Servidor listo para aceptar requerimiento de clientes...");
			
		Socket socketCliente = null;
		Mensaje mensaje = new Mensaje();
		
		//El servidor se queda eternamente escuchando peticiones
		while(true) {
			try {
				System.out.println("Escuchando peticiones...");
				//acepta peticion y devulve un socket cliente
				socketCliente = socketServidor.accept(); 
				System.out.println("Se acepto al cliente: " 
					+ socketCliente.getInetAddress());
				ChatHilo ch = new ChatHilo(socketCliente, mensaje);
				ch.start();
				
			} catch(IOException ioe) {
				System.out.println("Ha ocurrido un error al establecer conexion con un"
					+ " cliente!");
				ioe.printStackTrace();
			}
		}
	}

	private static int compruebaPuerto(String[] args) {
		if(args.length != 1) {
			System.out.println("Numero de argumentos invalidos. Por favor introduzca el "
			+ "numero del puerto");
			System.exit(1);
		}
		return Integer.valueOf(args[0]);
	}


}
