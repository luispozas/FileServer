package parte2.mensaje;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import parte2.servidor.MyFichero;

public abstract class Mensaje implements Serializable{

    private TipoMensaje tipo;
    private InetAddress origen;
    private InetAddress destino;

    public Mensaje(TipoMensaje tipo, InetAddress origen, InetAddress destino) {
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
    }

    public TipoMensaje getTipo() {
        return tipo;
    }

    public InetAddress getOrigen() {
        return origen;
    }

    public InetAddress getDestino() {
        return destino;
    }

    /* Operaciones definidas en clases heredadas. */
    public String getNombre(){ return null; }

    public ArrayList<MyFichero> getFicheros(){ return null; }

    public MyFichero getFichero(){ return null; }
    
    public String getNombreFichero(){ return null; }

    public ArrayList<String> getListaUsuarios(){ return null; }
    
    public int getPuerto(){ return -1; }
}
