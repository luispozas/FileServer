package parte2.servidor;

import java.util.ArrayList;

public class InfoFicherosUsuarios {
	private String _idUsuario;
	private ArrayList<MyFichero> _ficheros;
	
	public InfoFicherosUsuarios(String id, ArrayList<MyFichero> ficheros) {
		_idUsuario = id;
		_ficheros = ficheros;
	}
	
	public String getListFicheros() {
		String listaFicheros = "";
		for(MyFichero f : _ficheros) {
    		listaFicheros = listaFicheros + f.get_nombre() + " | ";
    	}
    	return listaFicheros;
	}
	
	public MyFichero getFichero(String nombreFichero) {
	 	for(MyFichero f : _ficheros) {
    		if(f.get_nombre().equals(nombreFichero)) return f;
    	}
    	return null;
	}
	
    public boolean estaFichero(String nombreFichero) {
    	for(MyFichero f : _ficheros) {
    		if(f.get_nombre().equals(nombreFichero)) return true;
    	}
    	return false;
    }
    
    public void addFichero(MyFichero fichero) {
    	_ficheros.add(fichero);
    }

	public String get_idUsuario() {
		return _idUsuario;
	}
}
