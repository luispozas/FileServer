package parte2.servidor;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ServidorFicheros {
	
	private int puerto_servidor;
	private ServerSocket ss;
	private MonitorServidorFicheros monitor;
	
	public ServidorFicheros(int puerto) {
		puerto_servidor = puerto;
		monitor = new MonitorServidorFicheros(10); /*Numero maximo de usuarios en las tablas del servidor.*/
		
		try {
			ss = new ServerSocket(puerto_servidor);
			while(true) {
				System.out.println("Servidor esperando...");
				Socket s = ss.accept();
				System.out.println("Cliente establecido.");
				(new OyenteCliente(new ObjectInputStream(s.getInputStream()), new ObjectOutputStream(s.getOutputStream()), this)).start();    
		    }
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/* Supongo que todos los usuarios tienen distinto nombre. */
	public void addUsuario(String nombre, ObjectInputStream fin, ObjectOutputStream fout, InetAddress ip, ArrayList<MyFichero> ficheros){
		monitor.addUsuario(nombre, fin, fout, ip, ficheros);
	}
	
	/* Obtengo todos los nombres de los usuarios activos y todos los ficheros. */
	public ArrayList<String> getNombresUsuario() throws InterruptedException {
        return monitor.getNombresUsuario();
    }
    
    /* Borro al usuario de "tabla_usuarios". */
	public void removeUsuario(String nombre) throws InterruptedException {
    	monitor.removeUsuario(nombre);
	}
    
    /* Busco al usuario que tiene ese nombre y devuelvo su flujo de salida. */
    public ObjectOutputStream getFOutClienteConNombre(String nombreUsuario){
		return monitor.getFOutClienteConNombre(nombreUsuario);
	}
    
    /* Busco al usuario que tiene el fichero y devuelvo su flujo de salida. */
    public ObjectOutputStream getFOutClienteConFichero(String nombreFichero) {
    	String idCliente = monitor.getUsuarioConFichero(nombreFichero);
    	return monitor.getFOutClienteConNombre(idCliente);
    }
    
    /* Busco el nombre de usuario que tiene el fichero "nombrefichero" */
    public String getUsuarioConFichero(String nombrefichero) {
    	return monitor.getUsuarioConFichero(nombrefichero);
    }
    
    /* Busco la direccion IP del usuario con nombre "idUsuario" */
    public InetAddress getIPUsuario(String idUsuario) {
    	return monitor.getIPUsuario(idUsuario);
    }
    
    /* Devuelve el objeto MyFichero dado un nombre de fichero.*/
    public MyFichero getMyfichero(String nombreFichero) throws InterruptedException {
    	return monitor.getMyFichero(nombreFichero);
    }
	
    public InetAddress getIpServidor() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace(); return null;
		}
	}
    
    public int get_puerto() {
    	return puerto_servidor;
    }
	
	public static void main(String[] args) {
		new ServidorFicheros(1234);
	}
}
