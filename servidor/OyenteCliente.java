package parte2.servidor;

import java.io.*;
import java.util.ArrayList;
import parte2.mensaje.*;

public class OyenteCliente extends Thread implements Runnable{
    private ObjectInputStream _fin;
    private ObjectOutputStream _fout;
    private ServidorFicheros _servidor;

    public OyenteCliente(ObjectInputStream fin, ObjectOutputStream out, ServidorFicheros servidor) {
        _fout = out;
		_fin = fin;
		_servidor = servidor;
    }

    @Override
    public void run() {
    	boolean close = false;
        while (!close) {
            try {
            	Mensaje msg = (Mensaje) _fin.readObject();
				switch (msg.getTipo()) {
                    case CONEXION:
                    	_servidor.addUsuario(msg.getNombre(), _fin, _fout, msg.getOrigen(), msg.getFicheros());
                        _fout.writeObject(new MsgConfConexion(_servidor.getIpServidor(), msg.getOrigen()));
                        break;
                        
                    case LISTA_USUARIOS:
                        ArrayList<String> nombres = _servidor.getNombresUsuario();
                        _fout.writeObject(new MsgConfListaUsuarios(_servidor.getIpServidor(), msg.getOrigen(), nombres));
                        break;
                        
                    case CERRAR_CONEXION:
                    	_servidor.removeUsuario(msg.getNombre());
                    	_fout.writeObject(new MsgConfCerrarConexion(_servidor.getIpServidor(), msg.getOrigen()));
                    	close = true;
                        break;
                    
                    case PEDIR_FICHERO:
                    	ObjectOutputStream foutCliente = _servidor.getFOutClienteConFichero(msg.getNombreFichero());
                    	if(foutCliente != null) {
                    		foutCliente.writeObject(new MsgEmitirFichero(msg.getOrigen(), 
                    								_servidor.getIPUsuario(_servidor.getUsuarioConFichero(msg.getNombreFichero())), 
                    								_servidor.getMyfichero(msg.getNombreFichero()), 
                    								msg.getNombre()));
                    	}
                    	else throw new Exception("No existe ningun usuario con ese fichero.");
                    	break;
                    	
                    case PREPARADO_CLIENTE_SERVIDOR:
                    	ObjectOutputStream foutCliente1 = _servidor.getFOutClienteConNombre(msg.getNombre());
                    	if(foutCliente1 != null) {
                    		foutCliente1.writeObject(new MsgPreparadoServidorCliente(msg.getOrigen(), 
                    								 msg.getDestino(), msg.getPuerto(), msg.getNombreFichero()));
                    	}
                    	else throw new Exception("Error, usuario no encontrado.");
                        break;
                        
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}