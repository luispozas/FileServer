package parte2.servidor;

import java.io.Serializable;

public class MyFichero implements Serializable{
	String _nombre;
	String _ruta;
	
	public MyFichero(String nombre, String ruta) {
		_nombre = nombre;
		_ruta = ruta;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_ruta() {
		return _ruta;
	}

	public void set_ruta(String _ruta) {
		this._ruta = _ruta;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		MyFichero mf = (MyFichero)o;
		if(mf.get_nombre().equals(_nombre) && mf.get_ruta().equals(_ruta)) {
			return true;
		}
		return false;
	}

}
