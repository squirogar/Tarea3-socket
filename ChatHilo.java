import java.net.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.LinkedList;

public class ChatHilo extends Thread implements Observer {

    private String nombre;
    private boolean flag_alias = true;
    private Socket socketCliente;
    private Mensaje mensaje;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private List<String> alias = new LinkedList<String>();
    
    public ChatHilo(Socket socketCliente, Mensaje mensaje) {
        this.socketCliente = socketCliente; // le pasamos el socket del cliente
        this.mensaje = mensaje; // le pasamos el objeto mensaje que es observable
    }

    //Evita que se elija un alias que este siendo ocupado por otro cliente
    private synchronized boolean setAlias(String nombre, PrintWriter out) {
        if(!alias.contains(nombre)){
        alias.add(nombre);
            out.println("alias asignado correctamente");
        return false;
        }
        out.println("el alias ingresado esta siendo ocupado por otra persona "
            + "dentro del chat. Intente con otro");
        return true;
    }
    
    /*Elimina el alias ocupado por un cliente que se quiera desconectar de 
    la lista de alias. Este alias queda disponible*/
    private synchronized void eliminaAlias(String nombre) {
        alias.remove(alias.indexOf(nombre));
        System.out.println("el alias " + nombre + " queda liberado!");
    }

    @Override
    public void run() {
        try {
            //Para enviar mensajes a cliente
            out = new PrintWriter(new OutputStreamWriter(
                socketCliente.getOutputStream()), true);
		    //Para recibir mensajes del cliente
		    in = new BufferedReader(new InputStreamReader(
                socketCliente.getInputStream()));
            
            out.println("Bienvenido al chat!");
            out.println("Escriba su nombre");
            try {
                while(flag_alias)   {
                    nombre = in.readLine();
                    flag_alias = setAlias(nombre, out); 
                }
                
                //Se agrega este hilo a la lista de observador del objeto mensaje
                synchronized(mensaje) {
                    mensaje.addObserver(this);
                }
                
                out.println("-- Para salir de la aplicacion ingrese /chao --");

                //Ciclo que controla la interaccion cliente-servidor
                while(true) {
                    //mensaje de cliente
                    String mensajeCliente = in.readLine();
                    if(mensajeCliente != null) {
                        if(mensajeCliente.equals("/chao")) {
                            break;
                        } else {
                            System.out.println("mensaje cliente " 
                                + socketCliente.getInetAddress() + ": " + mensajeCliente);   
                            //Hora
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            String hora = sdf.format(cal.getTime()); 
                            mensaje.setMsj(mensajeCliente, hora, nombre);
                        }
                    }
                }

                out.println("adios!");
                //Liberamos el alias ocupado por el cliente que se fue
                eliminaAlias(nombre);
                //Se termina la conexion
                cierraConexion();
                

            } catch(IOException ioe) {
                System.out.println("Ha ocurrido un error al comunicarse con cliente!");
            }
            
        } catch(IOException ioe) {
            System.out.println("Ha ocurrido un error en los streams de comunicacion"
                + " con el cliente");
            ioe.printStackTrace();
        } finally {
            cierraConexion();
        }    
        
	}

    /*Este metodo es llamado siempre que un objeto observable (en este caso mensaje)
    haya cambiado en algun lugar y se haya notificado su cambio a todos los 
    observadores.
    Recibe el objeto observable que cambio y un argumento (si no se proporciono uno
    es null)*/
    @Override
    public void update(Observable o, Object arg) {
        out.println("\n" + arg.toString());
    }

    //Cierra streams y socket
    private void cierraConexion() {
        try {
            out.close();
            in.close();
            socketCliente.close();
        } catch(IOException ioe) {
            System.out.println("Error al cerrar Streams y/o sockets");
            ioe.printStackTrace();
        }
        
    }

}
