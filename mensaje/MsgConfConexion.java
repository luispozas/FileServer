package parte2.mensaje;

import java.net.InetAddress;

public class MsgConfConexion extends Mensaje {

    public MsgConfConexion(InetAddress origen, InetAddress destino) {
        super(TipoMensaje.CONFIRMACION_CONEXION, origen, destino);
    }
}
