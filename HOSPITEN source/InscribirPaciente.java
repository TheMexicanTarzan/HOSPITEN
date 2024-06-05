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

public class InscribirPaciente extends JFrame implements ActionListener{

	
	TestConexion conector;
	JLabel nameLabel, sexoLabel, edadLabel, historialLabel, localidadLabel, datosLabel,
		   medicoLabel, areaLabel, Peso, Altura, Presion, Oxigenacion, Temperatura, Diagnostico,
		   Receta;
	JTextField nombreTextfield, edadTextfield, localidadTextfield, medicoTextfield, PesoT,
			   AlturaT, PresionT, OxigenacionT, TemperaturaT;
	JButton inscribir;
	String[] sexOptions = {" ", "M", "F"};
	String[] areaOptions = {"General", "Cardiología", "Cuidados intensivos", "Hematología", "Oncología", "Neumología",
						"Fisioterapia", "Pediatría", "Medicina interna", "Oftalmología", "Ginecología",  "Urología",
						"Otorrinolaringología"};
	JComboBox<String> sexoBox, areaBox;
	JTextArea historialTextArea, diagnosticoArea, RecetaT;
	JScrollPane scrollPane;
	LocalDate fechaActual;
	JFrame ventana;
	
	public InscribirPaciente(TestConexion conector) {
		this.conector = conector;
		Container container = getContentPane();
        container.setLayout(new FlowLayout());
        //ventana.add(container);

        nameLabel = new JLabel("Nombre el Paciente:");
        container.add(nameLabel);
        nombreTextfield = new JTextField(27);
        container.add(nombreTextfield);
        
        sexoLabel = new JLabel("Sexo:");
        container.add(sexoLabel);
        sexoBox = new JComboBox<String>(sexOptions);
        container.add(sexoBox);
        
        edadLabel = new JLabel("Edad:");
        container.add(edadLabel);
        edadTextfield = new JTextField(5);
        container.add(edadTextfield);
        
        localidadLabel = new JLabel("Localidad:");
        container.add(localidadLabel);
        localidadTextfield = new JTextField(20);
        container.add(localidadTextfield);

        historialLabel = new JLabel("Ingrese todo el historial clínico importante:");
        container.add(historialLabel);
        historialTextArea = new JTextArea(10,25);
        historialTextArea.setLineWrap(true);
        historialTextArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(historialTextArea);
        container.add(historialTextArea);
        
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
        
        Peso = new JLabel("Peso (Kg):");
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
        
        Receta = new JLabel("Receta Médica:");
        container.add(Receta);
        RecetaT = new JTextArea(3,25);
        RecetaT.setLineWrap(true);
        RecetaT.setWrapStyleWord(true);
        scrollPane = new JScrollPane(RecetaT);
        container.add(RecetaT);
        
        inscribir = new JButton("Inscribir");
        container.add(inscribir);
        inscribir.addActionListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(365, 800);
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == inscribir){
			
			boolean occur = false;
			String nombrePaciente = nombreTextfield.getText();
			String sexoPaciente = sexOptions[sexoBox.getSelectedIndex()];
			String edadPaciente = edadTextfield.getText();
			int edad = Integer.parseInt(edadPaciente);
			String localidadPaciente = localidadTextfield.getText();
			String historialPaciente = historialTextArea.getText();
			String medico = medicoTextfield.getText();
			int ID_Med = Integer.parseInt(medico);
			String areaVisitada = areaOptions[areaBox.getSelectedIndex()];
			String pesoPaciente = PesoT.getText();
			String alturaPaciente = AlturaT.getText();
			String presionSanguinea = PresionT.getText();
			String oxigenacionPaciente = OxigenacionT.getText();
			String temperaturaPaciente = TemperaturaT.getText();
			String diagnostico = diagnosticoArea.getText();
			String receta = RecetaT.getText();
			
			if(nombrePaciente != null && sexoPaciente != null && edad > 0 && edad < 120 && ID_Med >= 1000
					&& areaVisitada != null && pesoPaciente != null && alturaPaciente != null) {
				int ID = conector.inscribirPaciente(nombrePaciente, sexoPaciente, edad, historialPaciente, alturaPaciente);
				occur = conector.registrarVisita(ID, ID_Med, areaVisitada,pesoPaciente, presionSanguinea, oxigenacionPaciente,
						temperaturaPaciente, diagnostico,receta);
				if(occur) {
					JOptionPane.showMessageDialog(this, "Paciente inscrito, el ID del paciente es: "+ID+". Asegúrese de que lo recuerde"
							+ "\nVisita registrada correctamente");
				} else {
					JOptionPane.showMessageDialog(this, "Verifique una vez más los datos, ocurrió un error...");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Verifique una vez más los datos, ocurrió un error...");
			}
			
			System.out.println("Paciente inscrito");
		}
	}
}
