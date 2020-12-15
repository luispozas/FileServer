package parte2.mensaje;

import java.net.InetAddress;

public class MsgListaUsuarios extends Mensaje {

    public MsgListaUsuarios(InetAddress origen, InetAddress destino) {
        super(TipoMensaje.LISTA_USUARIOS, origen, destino);
    }
}
