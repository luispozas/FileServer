package parte2.mensaje;

import java.net.InetAddress;
import java.util.ArrayList;

public class MsgConfListaUsuarios extends Mensaje {

    private ArrayList<String> nombres;

    public MsgConfListaUsuarios(InetAddress origen, InetAddress destino, ArrayList<String> nombres) {
        super(TipoMensaje.CONFIRMACION_LISTA_USUARIOS, origen, destino);
        this.nombres = nombres;
    }

    @Override
    public ArrayList<String> getListaUsuarios() {
        return nombres;
    }
}
