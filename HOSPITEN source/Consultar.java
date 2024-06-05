package databaseProyecto;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Consultar extends JFrame implements ActionListener{

	TestConexion conector;
	QueryViewer viewer = new QueryViewer();
	JLabel sentencia, IDLabel, sexoLabel, edadLabel, medicoLabel, areaLabel, Diagnostico;
	JTextField IDTextfield, edadTextfield, medicoTextfield;
	JButton consultar;
	String[] sexOptions = {" ", "M", "F"};
	String[] areaOptions = {" ", "General", "Cardiología", "Cuidados intensivos", "Hematología", "Oncología", "Neumología",
					"Fisioterapia", "Pediatría", "Medicina interna", "Oftalmología", "Ginecología",  "Urología",
					"Otorrinolaringología"};
	JComboBox<String> sexoBox, areaBox;
	JTextArea historialTextArea, diagnosticoArea, RecetaT;
	JScrollPane scrollPane;
	LocalDate fechaActual;
	JFrame ventana;
	
	public Consultar(TestConexion conector) {
		this.conector = conector;
		Container container = getContentPane();
        container.setLayout(new FlowLayout());
        
        sentencia = new JLabel("Bienvenido al apartado para consultar, considera formular la consulta llenando un solo campo            ");
        container.add(sentencia);
        
        IDLabel = new JLabel("ID del paciente:");
        container.add(IDLabel);
        IDTextfield = new JTextField(7);
        container.add(IDTextfield);
        
        medicoLabel = new JLabel("    Médico que consulta:   ");
        container.add(medicoLabel);
        medicoTextfield = new JTextField(20);
        container.add(medicoTextfield);
        
        sexoLabel = new JLabel("Sexo:");
        container.add(sexoLabel);
        sexoBox = new JComboBox<String>(sexOptions);
        container.add(sexoBox);
        
        areaLabel = new JLabel("Área visitada:");
        container.add(areaLabel);
        areaBox = new JComboBox<String>(areaOptions);
        container.add(areaBox);
        
        Diagnostico = new JLabel("Diagnóstico:");
        container.add(Diagnostico);
        diagnosticoArea = new JTextArea(1,25);
        diagnosticoArea.setLineWrap(true);
        diagnosticoArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(diagnosticoArea);
        container.add(diagnosticoArea);
        container.add(scrollPane);
        
        consultar = new JButton("Realizar consulta");
        container.add(consultar);
        consultar.addActionListener(this);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(720, 170);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == consultar){
			viewer.limpiarTextArea();
		
			String idPaciente = IDTextfield.getText();
			int id;
			if(idPaciente.isBlank()) {
				id = 0;
			} else {
				id = Integer.parseInt(idPaciente);
			}
			String medico = medicoTextfield.getText();
			int id_Med;
			if(medico.isBlank()) {
				id_Med = 0;
			} else {
				id_Med = Integer.parseInt(medico);
			}
			String sexo = sexOptions[sexoBox.getSelectedIndex()];
			String areaVisitada = areaOptions[areaBox.getSelectedIndex()];
			String diagnostico = diagnosticoArea.getText();
			
			if(id >= 1) {
				conector.cidPac(viewer, id);
			}
			
			if(id_Med >= 1000) {
				conector.medPac(viewer, id_Med);
			}
			
			if(sexo != " ") {
				conector.sexPac(viewer, sexo);
			}
			
			if(areaVisitada != " ") {
				conector.areaPac(viewer, areaVisitada);
			}
			
			if(diagnostico != null) {
				conector.diagPac(viewer, diagnostico);
			}
			
			//conector.cidPac(viewer, 5);
			//conector.sexPac(viewer, "M");
			//conector.areaPac(viewer, "Cara");
			//conector.diagPac(viewer, "Sin problemas");
			//conector.medPac(viewer, 1000);
		}
	}
	
	
	
}
