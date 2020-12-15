package parte2.mensaje;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import parte2.servidor.MyFichero;

public class MsgConexion extends Mensaje implements Serializable{

    private String _nombreCliente;
    private ArrayList<MyFichero> _ficheros;

    public MsgConexion(InetAddress origen, InetAddress destino, String nombre, ArrayList<MyFichero> ficheros) {
        super(TipoMensaje.CONEXION, origen, destino);
        _nombreCliente = nombre;
        _ficheros = ficheros;
    }

    @Override
    public String getNombre() {
        return _nombreCliente;
    }

    @Override
    public ArrayList<MyFichero> getFicheros() {
        return _ficheros;
    }
}
