package parte2.cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ReceptorFichero extends Thread implements Runnable {
	
	private int _puerto;
	private Socket _sReceptor;
    private InetAddress _ipEmisor; /* Direccion ip del receptor para crear canal. */
    private PrintStream _consola;
	private String _nombreFichero;
    

    public ReceptorFichero(String nombreFichero, InetAddress ipEmisor, int puerto, PrintStream consola) {
    	_puerto = puerto;
    	_ipEmisor = ipEmisor;
    	_consola = consola;
    	_nombreFichero = nombreFichero;
    }
    
    @Override
    public void run() {
		try {
			_sReceptor = new Socket(_ipEmisor, _puerto);
			
			_consola.println("   Leyendo al emisor...");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(_sReceptor.getInputStream()));
			
			File archivo = new File(_nombreFichero); //Creo el fichero vacio.
			BufferedWriter write = new BufferedWriter(new FileWriter(archivo));
	        
	        String linea; //Linea leida.
	        while ((linea = in.readLine()) != null){
	        	write.write(linea); //Guardo el contenido en un fichero nuevo para el cliente.
	        	write.newLine();
	        }
	        write.flush();
	       
	        System.out.println("   Fichero (" + _nombreFichero + ") recibido");
	        
	        write.close();
	        _sReceptor.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
