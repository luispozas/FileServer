package parte2.servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class MonitorServidorFicheros {
	private int MAX_USUARIOS;
	private int numUsuarios;
	private ArrayList<InfoFlujosUsuarios> tabla_flujos_usuarios;
	private ArrayList<InfoFicherosUsuarios> tabla_ficheros_usuarios;
	
	public MonitorServidorFicheros(int maxUsuarios) {
		MAX_USUARIOS = maxUsuarios;
		numUsuarios = 0;
		tabla_flujos_usuarios = new ArrayList<>();
		tabla_ficheros_usuarios = new ArrayList<>();
		
	}
	
	synchronized public void addUsuario(String idCliente, ObjectInputStream fin, ObjectOutputStream fout, InetAddress ip, ArrayList<MyFichero> ficheros){
		while(numUsuarios == MAX_USUARIOS) {
			try {
				wait();
			} catch (InterruptedException e) { e.printStackTrace(); }
		}
		tabla_flujos_usuarios.add(new InfoFlujosUsuarios(idCliente, fin, fout, ip));
		tabla_ficheros_usuarios.add(new InfoFicherosUsuarios(idCliente, ficheros));
		numUsuarios++;
		
	}
	
	synchronized public ArrayList<String> getNombresUsuario() {
        ArrayList<String> nombres = new ArrayList<>();
        for (InfoFicherosUsuarios u : tabla_ficheros_usuarios) {
            nombres.add("[USUARIO]: " + u.get_idUsuario() + " -> [FICHEROS]: " + u.getListFicheros());
        }
        return nombres;
    }
	
	synchronized public void removeUsuario(String idUsuario){
    	for(InfoFlujosUsuarios u : tabla_flujos_usuarios) {
    		if(u.get_idUsuario().equals(idUsuario)) {
    			tabla_flujos_usuarios.remove(u);
    			break;
    		}
    	}
    	for(InfoFicherosUsuarios u: tabla_ficheros_usuarios) {
    		if(u.get_idUsuario().equals(idUsuario)) {
    			tabla_ficheros_usuarios.remove(u);
    			break;
    		}
    	}
    	numUsuarios--;
    	notifyAll();
	}
	
	synchronized ObjectOutputStream getFOutClienteConNombre(String idUsuario){
		for(InfoFlujosUsuarios u : tabla_flujos_usuarios) { 
    		if(u.get_idUsuario().equals(idUsuario)) {
            	return u.get_out();
            }
    	}
		return null;
	}

	synchronized public String getUsuarioConFichero(String nombrefichero) {
		for(InfoFicherosUsuarios u : tabla_ficheros_usuarios) { /* Busco en la tabla de ficheros el usuario que lo tiene */
    		if(u.estaFichero(nombrefichero)) {
    			return u.get_idUsuario();
            }
    	}
		return null;
	}

	synchronized public InetAddress getIPUsuario(String idUsuario) {
		for(InfoFlujosUsuarios u : tabla_flujos_usuarios) { 
    		if(u.get_idUsuario().equals(idUsuario)) {
            	return u.get_ip();
            }
    	}
		return null;
	}

	synchronized public MyFichero getMyFichero(String nombreFichero) {
		MyFichero f;
    	for(InfoFicherosUsuarios u : tabla_ficheros_usuarios) {
			if((f = u.getFichero(nombreFichero)) != null) {
    			return f;
    		}
    	}
		return null;
	}
}
