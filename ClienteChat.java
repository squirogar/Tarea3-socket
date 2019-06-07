import java.io.*;
import java.net.*;

public class ClienteChat {

    private static Socket clienteSocket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args) throws IOException {

        if (args.length != 2) { //si hay más de 1 parámetro
            System.out.println("Ingresar solo 2 argumento, la IP del servidor y "
            + "el PUERTO del servicio.");
            System.exit(1);
        } else {
            System.out.println("El cliente se va a conectar al servidor " + args[0] + " en el puerto " + args[1]+" ...");
        }

        Integer servicioPuerto = Integer.parseInt(args[1]);
        String servidorIP = args[0];
        
        try {
            System.out.println("Conectando ...");
            clienteSocket = new Socket(servidorIP, servicioPuerto);
            System.out.println("Conectando al servidor con IP: " + servidorIP 
                + ", al puerto: " + servicioPuerto);
        } catch (UnknownHostException e) {
            System.err.println("No conozco el servidor con IP: " + servidorIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No puedo conectarme al servidor con IP:  " + servidorIP);
            System.exit(1);
        }
       
        //Para escribir mensajes al cliente
        out = new PrintWriter(new OutputStreamWriter(
            clienteSocket.getOutputStream()), true);
        
        //Para recibir mensajes del cliente
        in = new BufferedReader(new InputStreamReader(
            clienteSocket.getInputStream()));

        HiloMensajesCliente hmc = new HiloMensajesCliente(out);
        hmc.start();
        
       

        //ciclo que controla la lectura de mensajes desde el servidor al cliente
        while (true) {
            try {
                String mensajeServidor = in.readLine();
                if(mensajeServidor != null) {
                    //si ha llegado un mensaje del servidor
                    System.out.println("mensaje servidor: " + mensajeServidor);
                }else{
                    //si se ha alcanzado el final del stream
                    System.out.println("\nse ha desconectado el servidor!");
                    cierraConexion();
                    System.exit(-1);
                }
                            
            } catch(IOException ioe) {
                System.out.println("Error de mensaje");
            }     
        }
    }

  
    //Cierra streams y socket
    private static void cierraConexion() {
        try {
            out.close();
            in.close();
            clienteSocket.close();
        } catch(IOException ioe) {
            System.out.println("Error al cerrar Streams y/o sockets");
            ioe.printStackTrace();
        }
        
    }
}
