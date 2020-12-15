package parte2.servidor;

import java.io.*;
import java.net.InetAddress;

public class InfoFlujosUsuarios {
	private String _idUsuario;
	private ObjectInputStream _in;
	private ObjectOutputStream _out;
	private InetAddress _ip;
	
	public InfoFlujosUsuarios(String id, ObjectInputStream in, ObjectOutputStream out, InetAddress ip) {
		_idUsuario = id;
		_in = in;
		_out = out;
		_ip = ip;
	}

	public String get_idUsuario() {
		return _idUsuario;
	}

	public ObjectInputStream get_in() {
		return _in;
	}

	public ObjectOutputStream get_out() {
		return _out;
	}

	public InetAddress get_ip() {
		return _ip;
	}
}
