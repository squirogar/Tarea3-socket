import java.net.*;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ChatHilo extends Thread implements Observer {
    private Socket socketCliente;
    private String nombre;
    private Mensaje mensaje;
    private PrintWriter out = null;
    private BufferedReader in = null;
    public ChatHilo(Socket socketCliente, Mensaje mensaje) {
        this.socketCliente = socketCliente; // le pasamos el socket del cliente
        this.mensaje = mensaje; // le pasamos el objeto mensaje que es observable
    }

    @Override
    public void run() {
        try {
            //Para enviar mensajes a cliente
            out = new PrintWriter(new OutputStreamWriter(
                socketCliente.getOutputStream(), "UTF_8"), true);
		    //Para recibir mensajes del cliente
		    in = new BufferedReader(new InputStreamReader(
                socketCliente.getInputStream(), "UTF-8"));
            
            out.println("Bienvenido al chat!");
            out.println("Escriba su nombre");
            try {
                //---------!comprobar que el alias no existe en el chat-------
                nombre = in.readLine();
                //------------------------------------------------------------
                //Se agrega este hilo a la lista de observador del objeto mensaje
                mensaje.addObserver(this);

                //Ciclo que controla la interaccion cliente-servidor
                while(true) {
                    //mensaje de cliente
                    String mensajeCliente = in.readLine();
                    System.out.println("mensaje cliente: " + mensajeCliente);   
                    //Hora
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    String hora = sdf.format(cal.getTime()); 
                    mensaje.setMsj(mensajeCliente, hora, nombre);
                }
            } catch(IOException ioe) {
                System.out.println("Ha ocurrido un error al comunicarse con cliente!");
            }
            
            //Se termina la conexion
            out.println("adios!");
            //Cerramos los streams para comunicarse con un cliente
            out.close();
            in.close();
        
        } catch(IOException ioe) {
            System.out.println("Ha ocurrido un error en los streams de comunicacion"
                + " con el cliente");
            ioe.printStackTrace();
        } finally {
            try {
                socketCliente.close();
            } catch(IOException ioe) {
                System.out.println("Ha ocurrido un error al cerrar el socket del "
                    + "cliente");
            }
        }    
        
	}

    /*Este metodo es llamado siempre que un objeto observable (en este caso mensaje)
    haya cambiado en algun lugar y se haya notificado su cambio a todos los 
    observadores.
    Recibe el objeto observable que cambio y un argumento (si no se proporciono uno
    es null)*/
    @Override
    public void update(Observable o, Object arg) {
        out.println(arg.toString());
    }

}