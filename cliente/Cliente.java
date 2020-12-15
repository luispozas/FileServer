package parte2.cliente;

import parte2.gui.ElegirFichero;
import parte2.mensaje.*;
import parte2.servidor.MyFichero;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente extends Thread {
	
	private int _puertoEmisores;
    private String _nombre; /* Nombre de usuario. */
    private InetAddress _ip; /* Direccion ip de la maquina. */
    private InetAddress _ipServidor; /* Direccion ip del servidor. */
    private int _puertoServidor; /* puerto del servidor. */
    private ArrayList<MyFichero> _ficheros; /* Ficheros que proporciona el usuario. */

    /* Comunicacion con el servidor. */
    private Socket _s;
    private ObjectOutputStream _fout;
    private ObjectInputStream _fin;

    /* Comunicacion con el usuario. */
    private Scanner _sc;
	

    public Cliente(InetAddress ip, int puertoInicioEmisores) {
        try {
        	_puertoEmisores = puertoInicioEmisores;
            _ip = ip;
            _sc = new Scanner(System.in);
            _ficheros = new ArrayList<>();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	/* Puertos nuevos para establecer conexion entre clientes. */
	public int getNewPort() throws InterruptedException {
		_puertoEmisores = _puertoEmisores + 1;
		return _puertoEmisores;
	}

    @Override
    public void run() {
    	try {
    		
    		/* Leo el nombre de usuario y sus ficheros desde el teclado. */
            String seguir;
            System.out.println("===========================================================");
            System.out.println("Introduzca el nombre de usuario: ");
            _nombre = _sc.nextLine();
            System.out.println("Introduzca la IP del servidor al que desea conectarse: ");
            _ipServidor = InetAddress.getByName(_sc.nextLine());
            System.out.println("Introduzca el puerto el servidor: ");
            _puertoServidor = _sc.nextInt();
            _sc.nextLine();
            
            ElegirFichero elegir = new ElegirFichero();
            MyFichero nf;
            do {
                System.out.println("Seleccione un fichero que desea compartir con otros clientes.");
                elegir.setVisible(true);
                try {
                	nf = elegir.getArchivo();
                    System.out.println("Su fichero elegido es: " +  nf.get_nombre());
                    if(!_ficheros.contains(nf)) _ficheros.add(nf);
                    else  System.err.println("Ese fichero ya ha sido introducido.");
                }
                catch(Exception e) {
                	System.out.println(e.getMessage());
                }
                System.out.println("Quiere introducir otro fichero? (s/n): ");
                seguir = _sc.nextLine();
                
            } while (!seguir.equals("n"));
            System.out.println("===========================================================\n");
            
            /* Creo el socket con el servidor. */
            _s = new Socket(_ipServidor, _puertoServidor);
            _fout = new ObjectOutputStream(_s.getOutputStream());
            _fin = new ObjectInputStream(_s.getInputStream());
            
            /* Creo nuevo Thread OyenteServidor para leer por el socket. */
            (new OyenteServidor( _fin, _fout, System.out, this)).start(); 
            
            /* Envio mensaje de conexion. */
            System.out.println("   Esperando a conectarse con el servidor...");
            _fout.writeObject(new MsgConexion(_ip, _ipServidor, _nombre, _ficheros));
            
            /* Espero hasta que el oyenteServidor me avise que se ha establecido la conexion.*/
            synchronized (this) {wait();}
            
       
            /* Establezco el menu con el usuario. */
            int opcion;
            do {
            	System.out.println(" ________________ OPERACIONES PARA REALIZAR ________________");
            	System.out.println("|                                                           |");
            	System.out.println("| Introduzca una opcion despues de la informacion:          |");
                System.out.println("| 1 - Consultar usuarios conectados.                        |");
                System.out.println("| 2 - Pedir fichero.                                        |");
                System.out.println("| 0 - Salir.                                                |");
                System.out.println(" ------------------------------------------------------------");
                System.out.println("--------------- INFORMACION DE LOS PROCESOS: ----------------");
                opcion = _sc.nextInt();
                _sc.nextLine();
              
                switch (opcion) {
                    case 1:
                        _fout.writeObject(new MsgListaUsuarios(_ip, _ipServidor));
                        break;
                        
                    case 2:
                        System.out.println("Introduzca el nombre del fichero: ");
                        String nombreFichero = _sc.nextLine();
                        /* Envio el fichero de deseo junto con mi id_cliente. */
                        _fout.writeObject(new MsgPedirFichero(_ip, _ipServidor, nombreFichero, _nombre));
                        break;
                        
                    case 0:
                        _fout.writeObject(new MsgCerrarConexion(_ip, _ipServidor, _nombre));
                        System.out.println("Conexion con el servidor cerrada.");
                        break;
                        
                    default:
                        System.out.println("Error. Opcion no reconocida.");
                }
            
            } while (opcion == 1 || opcion == 2);
            System.out.println("_______________ FIN OPERACIONES _____________");
            
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
	public static void main(String[] args) throws UnknownHostException {
		/* Direccion ip portatil = "192.168.0.68" */
		/* Direccion ip sobremesa = "127.0.1.1" */
		if(args.length != 1) return;
		int puertoInicioEmisores = Integer.valueOf(args[0]);
		
		new Cliente(InetAddress.getLocalHost(), puertoInicioEmisores).start();
	}
}
