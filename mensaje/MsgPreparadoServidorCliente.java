package parte2.mensaje;

import java.net.InetAddress;

public class MsgPreparadoServidorCliente extends Mensaje {

    private int _puerto;
    private String _nombreFichero;

    public MsgPreparadoServidorCliente(InetAddress origen, InetAddress destino, int puerto, String nombreFichero) {
        super(TipoMensaje.PREPARADO_SERVIDOR_CLIENTE, origen, destino);
        _puerto = puerto;
        _nombreFichero = nombreFichero;
    }
    
    @Override
    public int getPuerto() {
        return _puerto;
    }
    
    @Override
    public String getNombreFichero() {
    	return _nombreFichero;
    }
}
