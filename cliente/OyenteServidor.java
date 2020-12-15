package parte2.cliente;

import parte2.mensaje.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class OyenteServidor extends Thread implements Runnable{

    private ObjectInputStream _fin;
    private ObjectOutputStream _fout;
    private PrintStream _consola;
	private Cliente _cliente; //Para obtener los puertos.

    public OyenteServidor(ObjectInputStream fin, ObjectOutputStream out, PrintStream consola, Cliente cliente) {
        _consola = consola;
		_fin = fin;
		_fout = out;
		_cliente = cliente;
    }

    @Override
    public void run() {
    	boolean close = false;
        while (!close) {
            try {
            	Mensaje msg = (Mensaje) _fin.readObject();
                switch (msg.getTipo()) {
                    case CONFIRMACION_CONEXION:
                        _consola.println("   Conexion establecida.");
                        synchronized (_cliente) {_cliente.notify();} /* Despierto al cliente para que empiece a pedir cosas al servidor.*/
                        break;
                        
                    case CONFIRMACION_LISTA_USUARIOS:
                        ArrayList<String> listaUsuarios = msg.getListaUsuarios();
                        _consola.println("   Lista de usuarios: ");
                        for (String s : listaUsuarios)
                        	_consola.println("   -> " + s);
                        break;
                        
                    case EMITIR_FICHERO:
                    	_consola.println("   Emisor listo para transimitir: " + msg.getFichero().get_nombre());
                    	int nuevoPuerto = _cliente.getNewPort();
                    	/* Preparo el emisor para que espere a que el receptor se una. Comunicacion pear to pear. */
                    	System.out.println("   Puerto nuevo para conexion: " + nuevoPuerto);
                        (new EmisorFichero(msg.getFichero(), nuevoPuerto, _consola)).start();
                        /* Envio puerto para que se conecte el receptor. IP_ORIGEN: propietario, IP_DESTINO: cliente que lo ha pedido. */ 
                        _fout.writeObject(new MsgPreparadoClienteServidor(msg.getDestino(), msg.getOrigen(), nuevoPuerto, msg.getFichero().get_nombre(), msg.getNombre()));
                        break;
                        
                    case PREPARADO_SERVIDOR_CLIENTE:
                    	_consola.println("   Receptor listo para recibir." );
                        (new ReceptorFichero(msg.getNombreFichero(), msg.getOrigen(), msg.getPuerto(), _consola)).start(); /* Envia la IP del emisor que esta en origen.*/
                        break;
                        
                    case CONFIRMACION_CERRAR_CONEXION:
                    	_consola.println("   Adios.");
                    	_fin.close();
                    	_fout.close();
                    	close = true;
                    	break;
                    	
                    default:
                        throw new IllegalArgumentException(msg.getTipo().toString());
                }
                
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
}
