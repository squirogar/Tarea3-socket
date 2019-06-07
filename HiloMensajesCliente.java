import java.io.*;
import java.util.*;

public class HiloMensajesCliente extends Thread {
    PrintWriter out;

    public HiloMensajesCliente(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entro al run");
        
        /*Este ciclo controlara la lectura de mensajes desde el cliente al 
        servidor*/
        while(true) {
            out.println(sc.nextLine());
        }
    }
}
