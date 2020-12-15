package parte2.gui;

import java.io.File;
import javax.swing.*;

import parte2.servidor.MyFichero;

public class ElegirFichero extends JFrame {
	private JFileChooser jFileChooser1;

    public ElegirFichero() {
        initComponents();
    }

    private void initComponents() {

        jFileChooser1 = new JFileChooser();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }
    
	public MyFichero getArchivo() throws Exception {
		String _ruta; /* Rutas de ficheros que proporciona el usuario. */
		String _nombre;
		
		if(jFileChooser1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File f = jFileChooser1.getSelectedFile();
			_ruta = f.getAbsolutePath();
			_nombre = f.getName();
		}
		else throw new Exception("Error al buscar el archivo que ha selecionado.");
		return new MyFichero(_nombre, _ruta);
	}
}
