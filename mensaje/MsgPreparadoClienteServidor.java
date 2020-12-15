package parte2.mensaje;

import java.net.InetAddress;

public class MsgPreparadoClienteServidor extends Mensaje {

    private int _puerto;
    private String _nombreFichero;
    private String _nombreUsuarioDestino;

    public MsgPreparadoClienteServidor(InetAddress origen, InetAddress destino, int puerto, String nombreFichero, String nombreUsuarioDestino) {
        super(TipoMensaje.PREPARADO_CLIENTE_SERVIDOR, origen, destino);
        _puerto = puerto;
        _nombreFichero = nombreFichero;
        _nombreUsuarioDestino = nombreUsuarioDestino;
    }
    
    @Override
    public int getPuerto() {
        return _puerto;
    }
    
    @Override
    public String getNombreFichero() {
    	return _nombreFichero;
    }
    
    @Override
    public String getNombre() {
    	return _nombreUsuarioDestino;
    }
}
