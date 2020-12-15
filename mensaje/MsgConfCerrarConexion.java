package parte2.mensaje;

import java.net.InetAddress;

public class MsgConfCerrarConexion extends Mensaje {

    public MsgConfCerrarConexion(InetAddress origen, InetAddress destino) {
        super(TipoMensaje.CONFIRMACION_CERRAR_CONEXION, origen, destino);
    }
}
