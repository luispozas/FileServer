package parte2.mensaje;

import java.net.InetAddress;

import parte2.servidor.MyFichero;

public class MsgEmitirFichero extends Mensaje {

    private MyFichero _fichero;
    private String _nombreCliente;

    public MsgEmitirFichero(InetAddress origen, InetAddress destino, MyFichero fichero, String nombreCliente) {
        super(TipoMensaje.EMITIR_FICHERO, origen, destino);
        _fichero = fichero;
        _nombreCliente = nombreCliente;
    }
    
    @Override
    public MyFichero getFichero() {
        return _fichero;
    }
    
    @Override
    public String getNombre() {
        return _nombreCliente;
    }
}
