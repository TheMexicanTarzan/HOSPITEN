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

public class RegistrarVisita extends JFrame implements ActionListener{

	TestConexion conector;
	JLabel IDLabel, sexoLabel, edadLabel, historialLabel, localidadLabel, datosLabel,
		   medicoLabel, areaLabel, Peso, Altura, Presion, Oxigenacion, Temperatura, Diagnostico,
		   Receta;
	JTextField IDTextfield, edadTextfield, localidadTextfield, medicoTextfield, PesoT,
			   AlturaT, PresionT, OxigenacionT, TemperaturaT;
	JButton inscribir;
	String[] areaOptions = {"General", "Cardiología", "Cuidados intensivos", "Hematología", "Oncología", "Neumología",
						"Fisioterapia", "Pediatría", "Medicina interna", "Oftalmología", "Ginecología",  "Urología",
						"Otorrinolaringología"};
	JComboBox<String> areaBox;
	JTextArea historialTextArea, diagnosticoArea, RecetaT;
	JScrollPane scrollPane;
	LocalDate fechaActual;
	JFrame ventana;
	
	public RegistrarVisita(TestConexion conector) {
		this.conector = conector;
		Container container = getContentPane();
        container.setLayout(new FlowLayout());
        //ventana.add(container);

        IDLabel = new JLabel("ID del Paciente:");
        container.add(IDLabel);
        IDTextfield = new JTextField(7);
        container.add(IDTextfield);
        
        datosLabel = new JLabel("   Datos del paciente recaudados por el médico:   ");
        container.add(datosLabel);
        
        medicoLabel = new JLabel("Médico que consulta:");
        container.add(medicoLabel);
        medicoTextfield = new JTextField(20);
        container.add(medicoTextfield);
        
        areaLabel = new JLabel("Área visitada:");
        container.add(areaLabel);
        areaBox = new JComboBox<String>(areaOptions);
        container.add(areaBox);
        
        Peso = new JLabel("   Peso (Kg):      ");
        container.add(Peso);
        PesoT = new JTextField(5);
        container.add(PesoT);
        
        Altura = new JLabel("Altura (cm):");
        container.add(Altura);
        AlturaT = new JTextField(5);
        container.add(AlturaT);
        
        Presion = new JLabel("Presión Sanguínea:");
        container.add(Presion);
        PresionT = new JTextField(5);
        container.add(PresionT);
        
        Oxigenacion = new JLabel("Oxigenación:");
        container.add(Oxigenacion);
        OxigenacionT = new JTextField(5);
        container.add(OxigenacionT);
        
        Temperatura = new JLabel("Temperatura (C°):");
        container.add(Temperatura);
        TemperaturaT = new JTextField(5);
        container.add(TemperaturaT);
        
        Diagnostico = new JLabel("Diagnóstico:");
        container.add(Diagnostico);
        diagnosticoArea = new JTextArea(1,25);
        diagnosticoArea.setLineWrap(true);
        diagnosticoArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(diagnosticoArea);
        container.add(diagnosticoArea);
        container.add(scrollPane);
        
        Receta = new JLabel("Receta Médica:");
        container.add(Receta);
        RecetaT = new JTextArea(3,25);
        RecetaT.setLineWrap(true);
        RecetaT.setWrapStyleWord(true);
        scrollPane = new JScrollPane(RecetaT);
        container.add(RecetaT);
        container.add(scrollPane);
        
        inscribir = new JButton("Registrar Visita");
        container.add(inscribir);
        inscribir.addActionListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(385, 500);
        setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == inscribir){
			
			boolean occur = false;
			String idPaciente = IDTextfield.getText();
			int id = Integer.parseInt(idPaciente);
			String medico = medicoTextfield.getText();
			int id_Med = Integer.parseInt(medico);
			String areaVisitada = areaOptions[areaBox.getSelectedIndex()];
			String pesoPaciente = PesoT.getText();
			String presionSanguinea = PresionT.getText();
			String oxigenacionPaciente = OxigenacionT.getText();
			String temperaturaPaciente = TemperaturaT.getText();
			String diagnostico = diagnosticoArea.getText();
			String receta = RecetaT.getText();
			
			if(id >= 1 && id_Med >= 1000 && areaVisitada != null) {
				occur = conector.registrarVisita(id, id_Med, areaVisitada, pesoPaciente, presionSanguinea, 
						oxigenacionPaciente, temperaturaPaciente, diagnostico, receta);
				if(occur) {
					JOptionPane.showMessageDialog(this, "Visita registrada correctamente");
				} else {
					JOptionPane.showMessageDialog(this, "Verifique una vez más los datos, ocurrió un error...");
				}
			}
		}
	}
}
