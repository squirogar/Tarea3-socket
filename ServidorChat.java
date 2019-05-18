import java.net.*;
import java.io.*;


public class ServidorChat {
  public static void main(String[]args) {
    ServerSocket socketServidor = null;
    boolean listening = true;
    Socket socketCliente = null;
    String entrada = "";
    try {
    	socketServidor = new ServerSocket(4444);
    	System.out.println("Se creó socket del servidor en el puerto 4444");
    } catch (IOException e) {
        System.out.println("Error, el puerto 4444 está siendo ocupado por otra"
          + "aplicacion");
        //deberia salir
        System.exit(1);
    }

    System.out.println("Servidor listo para aceptar requerimiento de "
    	+ "clientes...");
    
    try{
		while(listening) {
			System.out.println("Escuchando peticiones...");
			socketCliente = socketServidor.accept();
			System.out.println("Se acepto a un cliente");
      System.out.println("Se acepto a un cliente");
		  //-----------------------BAAAAAAD--------------------------
		  //Para enviar mensajes a cliente
		  PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true);
		  //Para recibir mensajes del cliente
		  BufferedReader in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
		  
				   entrada = in.readLine();
								System.out.println("Llego desde el cliente el mensaje: " + entrada);
								out.println("mensaje: " + entrada + " recibido.");
		  
		  //------------------------WRONG--------------------------
		  
		  System.out.println("Cliente " + socketCliente.getInetAddress()+ " se desconecto.");
				out.close();
				in.close();
		  socketCliente.close();
		}
    } catch(IOException ioe) {
		System.out.println("Ha ocurrido un error al escuchar al cliente");
		ioe.printStackTrace();
    }
    
  }
}
