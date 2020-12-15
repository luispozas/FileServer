package parte2.mensaje;

import java.net.InetAddress;

public class MsgCerrarConexion extends Mensaje {
	
	private String _nombreCliente;
	
    public MsgCerrarConexion(InetAddress origen, InetAddress destino, String nombre) {
        super(TipoMensaje.CERRAR_CONEXION, origen, destino);
        _nombreCliente = nombre;
    }
    
    @Override
    public String getNombre() {
        return _nombreCliente;
    }
}
