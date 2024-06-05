package databaseProyecto;

import javax.swing.*;
import java.awt.*;

public class QueryViewer extends JFrame {
    private JTextArea textArea;

    public QueryViewer() {
        // Configuración de la ventana
        setTitle("Consultas de Base de Datos");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear un área de texto para mostrar las consultas
        textArea = new JTextArea();
        textArea.setEditable(false); // Para evitar la edición del texto

        // Crear un JScrollPane y agregar el JTextArea
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Agregar el JScrollPane a la ventana
        getContentPane().add(scrollPane);

        setVisible(true);

        // Ejemplo de uso de setText para agregar una consulta
        agregarConsulta("SELECT * FROM usuarios;");

        // Ejemplo de uso de limpiar el textArea
        limpiarTextArea();
    }

    // Método para agregar una consulta al área de texto
    public void agregarConsulta(String consulta) {
        textArea.setText(textArea.getText() + consulta + "\n\n");
    }

    // Método para limpiar el contenido del área de texto
    public void limpiarTextArea() {
        textArea.setText("");
    }
}
