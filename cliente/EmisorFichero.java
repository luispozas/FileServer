package parte2.cliente;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import parte2.servidor.MyFichero;

public class EmisorFichero extends Thread  implements Runnable{
	
	private int _puerto;
	private MyFichero _fichero;
	private Socket _sEmisor;
	private PrintStream _consola;

    public EmisorFichero(MyFichero fichero, int puerto, PrintStream consola) {
    	_puerto = puerto;
    	_fichero = fichero;
    	_consola = consola;
    }
    
    @Override
    public void run() {
		try {
			ServerSocket server = new ServerSocket(_puerto);
			
			_consola.println("   Solicitud de envio del fichero (" + _fichero.get_nombre() + "), esperando al receptor.");
			_sEmisor = server.accept();
			
			
			PrintWriter out = new PrintWriter(_sEmisor.getOutputStream(), true);
			
        	BufferedReader read = new BufferedReader(new FileReader(_fichero.get_ruta()));
	         	 
         	//Envio del fichero
         	String linea;
         	while((linea = read.readLine()) != null) {
         		out.println(linea);
         	}
         	
            System.out.println("   Archivo (" + _fichero.get_nombre() + ") enviado.");
            read.close();
	        out.close();
	        server.close();
  	        
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
