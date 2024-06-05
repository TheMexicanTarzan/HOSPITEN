package databaseProyecto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio implements ActionListener {
	
	TestConexion conector;
	private JLabel labelConImagen;
    private JPanel panelBotones;
    private JButton consultar;
    private JButton visita;
    private JButton inscribir;
	JFrame ventana;
		 
	public Inicio(TestConexion conector) {
		this.conector = conector;
		SwingUtilities.invokeLater(() -> {
			ventana = new JFrame("BIENVENIDO AL PORTAL HOSPITEN");
		    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    ventana.setSize(600, 600);
		            
		    FondoPanel fondoPanel = new FondoPanel();
		    fondoPanel.cargarImagen(); 
			ventana.setContentPane(fondoPanel); 
			
		    labelConImagen = new JLabel();
		    labelConImagen.setLayout(null);
		    ventana.add(labelConImagen);
		    
		    inscribir = new JButton("Inscribir paciente");
		    inscribir.setBounds(265, 400, 150, 50);
		    inscribir.addActionListener(this); // Agregar ActionListener
		    ventana.add(inscribir);
		    
		    visita = new JButton("Registrar visita");
		    visita.setBounds(265, 400, 150, 50);
		    visita.addActionListener(this); // Agregar ActionListener
		    ventana.add(visita);
		    
		    consultar = new JButton("Consultar");
		    consultar.setBounds(265, 470, 150, 50);
		    consultar.addActionListener(this); // Agregar ActionListener
		    ventana.add(consultar);

		    panelBotones = new JPanel();
		    panelBotones.setOpaque(false);
		    labelConImagen.add(panelBotones);
		         
		    ventana.setVisible(true);
		}); 
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inscribir) {
            new InscribirPaciente(conector);
            
        } else if (e.getSource() == visita) {
            new RegistrarVisita(conector);
            
        } else if(e.getSource() == consultar) {
        	new Consultar(conector);
        	
        }
    }

	class FondoPanel extends JPanel {
	    private BufferedImage imagenFondo;

	    public FondoPanel() {
	        super();
	        cargarImagen();
	    }

	    private void cargarImagen() {
	        try {
	            imagenFondo = ImageIO.read(new File("C:\\Users\\pelon\\OneDrive\\Imágenes\\Capturas de pantalla\\logo.png"));
	        } catch (IOException e) {
	            System.out.println("Error al cargar la imagen: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (imagenFondo != null) {
	            g.drawImage(imagenFondo, 0, 0, this.getWidth(), this.getHeight(), this);
	        }
	    }
	}
	
 }

