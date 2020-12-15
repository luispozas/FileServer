package parte2.mensaje;

import java.net.InetAddress;

public class MsgPedirFichero extends Mensaje {

    private String _fichero;
    private String _nombreSolicitante;

    public MsgPedirFichero(InetAddress origen, InetAddress destino, String fichero, String nombreSolicitante) {
        super(TipoMensaje.PEDIR_FICHERO, origen, destino);
        _fichero = fichero;
        _nombreSolicitante = nombreSolicitante;
    }
    
    @Override
    public String getNombreFichero() {
        return _fichero;
    }
    
    @Override
    public String getNombre() {
    	return _nombreSolicitante;
    }
}
