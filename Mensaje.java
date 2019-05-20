//Clase Mensaje
import java.util.Observable;

public class Mensaje extends Observable {
    private String mensaje = null;
    private String hora = null;
    private String autor = null;

    public Mensaje() {
    }

    //Notifica a todos los clientes dentro del chat que un cliente ha escrito un
    //mensaje
    public void setMsj(String mensaje, String hora, String autor) {
        this.mensaje = mensaje;
        this.hora = hora;
        this.autor = autor;
        //Marca al objeto observable (msj) como cambiado
        this.setChanged();
        //Notificamos a todos los observadores (ChatHilo) que este objeto Observable
        //cambio
        this.notifyObservers(toString());
    }
    
    @Override
    public String toString() {
        return new StringBuilder(hora).append(" ").append(autor).append(": ")
            .append(mensaje).toString();
    }
}
