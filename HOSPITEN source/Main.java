package databaseProyecto;
import javax.swing.SwingUtilities;
import java.io.File;

public class Main{

	public static void main(String[] args) {
		TestConexion conector = new TestConexion();
        new Inicio(conector);
        
        
    }
}