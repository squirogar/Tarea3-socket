import java.io.*;
import java.net.*;

public class Clientechat {

    public static void main(String[] args) throws IOException {

        Socket clienteSocket = null;
        DataOutputStream mensajeSalidaDelCliente = null;
        DataInputStream mensajeEntradaAlCliente = null;
        Integer servicioPuerto = 4444;
        String servidorIP = "192.168.122.94";
        String entradaRemota = "";
        String mensajeEscritoPorElUsuario = "";
        boolean iterar = true;

        try {
            System.out.println("Conectando ...");
            clienteSocket = new Socket(servidorIP, servicioPuerto);
            System.out.println("Conectando al servidor con IP: " + servidorIP + ", al puerto: " + servicioPuerto);
        } catch (UnknownHostException e) {
            System.err.println("No conosco el servidor con IP: " + servidorIP);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No puedo conectarme al servidor con IP:  " + servidorIP);
            System.exit(1);
        }
        System.out.println("conecto gg");

        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                clienteSocket.getOutputStream()), true);
            //Para recibir mensajes del cliente
          BufferedReader  in = new BufferedReader(new InputStreamReader(
        clienteSocket.getInputStream(), "UTF-8"));

        while (true) {
            String a = in.readLine();
            if (a != null && !a.equals("chao")) {
                out.println("ggg");
                System.out.println(a);
            } else {
                iterar = false;
            }
        }
     
    }
}
