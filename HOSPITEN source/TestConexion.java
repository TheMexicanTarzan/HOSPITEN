package databaseProyecto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;

public class TestConexion {

    Conexion conexion;
    LocalDate fecha;
    java.sql.Date sqlDate;
    QueryViewer viewer;

    public TestConexion() {
        conexion = new Conexion();
    }

    public void mostrarDatos() {
        Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT * FROM doctor");

            while(rs.next()) {
                int Id_Medico = rs.getInt(1);
                String nombre_Medico = rs.getString(2);
                String especialidad = rs.getString(3);

                System.out.println(Id_Medico + " - " + nombre_Medico + " - " + especialidad);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertarDatos(int LaptopModel, int LaptopSpeed, int LaptopRAM, int LaptopHDD, String LaptopScreen, int LaptopPrice) {
        Connection cn = null;
        Statement stm = null;

        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            stm.executeUpdate("INSERT INTO Laptop VALUES (" + LaptopModel + ", " + LaptopSpeed + ", " +
                    LaptopRAM + ", " + LaptopHDD + ", '" + LaptopScreen + "', " + LaptopPrice + ")");
            System.out.println("Datos insertados correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int inscribirPaciente(String nombre, String sexo, int edad, String historial, String altura) {
        Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        int id = 0;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT MAX(ID) FROM paciente;");
            if(rs.next()) {
            	id = rs.getInt(1);
            }
            id = id + 1;
            
            stm.executeUpdate("INSERT INTO paciente (ID, NOMBRE, SEXO, EDAD, HISTORIAL, ALTURA) VALUES ('" + id + "', '" + nombre + "', '" + sexo + "', '" +
                    edad + "', '" + historial + "', '" + altura + "')");
            System.out.println("Datos insertados correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
    
    public boolean registrarVisita(int ID_Paciente, int ID_Medico, String area, String peso, String presion,
    							String oxigenacion, String temperatura, String diagnostico, String receta) {
        boolean occur = false;
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        int numCita = 0;
        fecha = LocalDate.now();
        //String fechaFormateada = sqlDate.toString();
        String fechA = fecha.toString();
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT MAX(`NUMERO DE VISITA`) FROM visita;");
            if(rs.next()) {
            	numCita = rs.getInt(1);
            }
            numCita = numCita + 1;
            
            stm.executeUpdate("INSERT INTO visita (`PACIENTE (ID)`, `DOCTOR (ID)`, AREA, `PESO (KG)`, PRESION, OXIGENACION, TEMPERATURA, DIAGNOSTICO, RECETA, `NUMERO DE VISITA`, FECHA)"
            		+ " VALUES ('" + ID_Paciente + "', '" + ID_Medico + "', '" + area + "', '" + peso + "', '" + presion + "', '" + oxigenacion + "', '" 
            		+ temperatura + "', '" + diagnostico + "', '" + receta + "', '" + numCita + "', '" + fechA +"')");

            occur = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return occur;
    }

    public void eliminarTupla(int LaptopModel) {
        Connection cn = null;
        Statement stm = null;

        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            stm.executeUpdate("DELETE FROM Laptop WHERE model = " + LaptopModel);
            System.out.println("Tupla eliminada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void cidPac(QueryViewer viewer, int idPaciente) {
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        ResultSet result = null;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            rs = stm.executeQuery("SELECT * FROM paciente WHERE `ID` = " + idPaciente + ";");
            
            StringBuilder string = new StringBuilder("Historia clínica:\n");
            //StringBuilder string = " ";
            while(rs.next()) {
            	int ID_Paciente = rs.getInt(1);
                String nombre = rs.getString(2);
                String sexo = rs.getString(3);
                String edad = rs.getString(4);
                String historial = rs.getString(5);
                String altura = rs.getString(6);

                string.append("ID: " + ID_Paciente + "\nNombre: " + nombre + "\nSexo: " + sexo + "\nEdad: " + edad 
                		+ "\nAltura: " + altura + "\nHistorial: " + historial + "\n");
            }
            result = stm.executeQuery("SELECT * FROM visita WHERE `PACIENTE (ID)` = " + idPaciente + ";");
            string.append("\nConsultas:");
            while(result.next()) {
            	int ID_Paciente = result.getInt(1);
            	int ID_doctor = result.getInt(2);
                String area = result.getString(3);
                String peso = result.getString(4);
                String presion = result.getString(5);
                String oxigenacion = result.getString(6);
                String temperatura = result.getString(7);
                String diagnostico = result.getString(8);
                String receta = result.getString(9);
                String numerovis = result.getString(10);
                String fecha = result.getString(11);
                
                string.append("\nID de paciente: " + ID_Paciente + "\n ID del doctor: " + ID_doctor + "\n Area: " + area + "\nPeso: " + peso 
                		+ "\nPresion: " + presion + "\nOxigenacion: " + oxigenacion + "\nTemperatura: " + temperatura + "\nDiagnostico: " + diagnostico + "\nReceta: "
                		+ receta + "\nNúmero de visita: " + numerovis + " \nFecha: " + fecha + "\n");
            }
            viewer.agregarConsulta(string.toString());
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //return rs;
    	
    }
    
    public void sexPac(QueryViewer viewer, String sexo) {
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            
            StringBuilder string = new StringBuilder("Consultas:\n");
            
            rs = stm.executeQuery("SELECT * FROM paciente RIGHT JOIN visita ON paciente.`ID` = visita.`PACIENTE (ID)` WHERE SEXO = '" + sexo + "' ;");
            
            while(rs.next()) {
            	int ID_Paciente = rs.getInt(7);
            	int ID_doctor = rs.getInt(8);
                String area = rs.getString(9);
                String peso = rs.getString(10);
                String presion = rs.getString(11);
                String oxigenacion = rs.getString(12);
                String temperatura = rs.getString(13);
                String diagnostico = rs.getString(14);
                String receta = rs.getString(15);
                String numerovis = rs.getString(16);
                String fecha = rs.getString(17);
                
                
                
                string.append("ID de paciente: " + ID_Paciente + "\nID del doctor: " + ID_doctor + "\nArea: " + area + "\nPeso: " + peso 
                		+ "\nPresion: " + presion + "\nOxigenacion: " + oxigenacion + "\nTemperatura: " + temperatura + "\nDiagnostico: " + diagnostico + "\nReceta: "
                		+ receta + "\nNumero de visita: " + numerovis + " \nFecha: "+ fecha +"\n\n");
            }
            viewer.agregarConsulta(string.toString());
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void areaPac(QueryViewer viewer, String Area) {
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            
            StringBuilder string = new StringBuilder("Consultas:\n");
            
            rs = stm.executeQuery("SELECT * FROM visita WHERE AREA = '" + Area + "' ;");
            
            while(rs.next()) {
            	int ID_Paciente = rs.getInt(1);
            	int ID_doctor = rs.getInt(2);
                String area = rs.getString(3);
                String peso = rs.getString(4);
                String presion = rs.getString(5);
                String oxigenacion = rs.getString(6);
                String temperatura = rs.getString(7);
                String diagnostico = rs.getString(8);
                String receta = rs.getString(9);
                String numerovis = rs.getString(10);
                String fecha = rs.getString(11);
                
                
                
                string.append("ID de paciente: " + ID_Paciente + "\nID del doctor: " + ID_doctor + "\nArea: " + area + "\nPeso: " + peso 
                		+ "\nPresion: " + presion + "\nOxigenacion: " + oxigenacion + "\nTemperatura: " + temperatura + "\nDiagnostico: " + diagnostico + "\nReceta: "
                		+ receta + "\nNumero de visita: " + numerovis + " \nFecha: "+ fecha +"\n\n");
            }
            viewer.agregarConsulta(string.toString());
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void diagPac(QueryViewer viewer, String Diagnostico) {
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            
            StringBuilder string = new StringBuilder("Consultas:\n");
            
            rs = stm.executeQuery("SELECT * FROM visita WHERE DIAGNOSTICO = '" + Diagnostico + "' ;");
            
            while(rs.next()) {
            	int ID_Paciente = rs.getInt(1);
            	int ID_doctor = rs.getInt(2);
                String area = rs.getString(3);
                String peso = rs.getString(4);
                String presion = rs.getString(5);
                String oxigenacion = rs.getString(6);
                String temperatura = rs.getString(7);
                String diagnostico = rs.getString(8);
                String receta = rs.getString(9);
                String numerovis = rs.getString(10);
                String fecha = rs.getString(11);
                
                string.append("ID de paciente: " + ID_Paciente + "\nID del doctor: " + ID_doctor + "\nArea: " + area + "\nPeso: " + peso 
                		+ "\nPresion: " + presion + "\nOxigenacion: " + oxigenacion + "\nTemperatura: " + temperatura + "\nDiagnostico: " + diagnostico + "\nReceta: "
                		+ receta + "\nNumero de visita: " + numerovis + " \nFecha: "+ fecha +"\n\n");
            }
            viewer.agregarConsulta(string.toString());
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void medPac(QueryViewer viewer, int ID_Med) {
    	Connection cn = null;
        Statement stm = null;
        ResultSet rs = null;
        try {
            cn = conexion.conectar();
            stm = cn.createStatement();
            
            StringBuilder string = new StringBuilder("Consultas dadas por el Médico con ID " + ID_Med + ":\n");
            
            rs = stm.executeQuery("SELECT * FROM visita WHERE `DOCTOR (ID)` = " + ID_Med + " ;");
            
            while(rs.next()) {
            	int ID_Paciente = rs.getInt(1);
            	int ID_doctor = rs.getInt(2);
                String area = rs.getString(3);
                String peso = rs.getString(4);
                String presion = rs.getString(5);
                String oxigenacion = rs.getString(6);
                String temperatura = rs.getString(7);
                String diagnostico = rs.getString(8);
                String receta = rs.getString(9);
                String numerovis = rs.getString(10);
                String fecha = rs.getString(11);
                
                string.append("ID de paciente: " + ID_Paciente + "\nID del doctor: " + ID_doctor + "\nArea: " + area + "\nPeso: " + peso 
                		+ "\nPresion: " + presion + "\nOxigenacion: " + oxigenacion + "\nTemperatura: " + temperatura + "\nDiagnostico: " + diagnostico + "\nReceta: "
                		+ receta + "\nNumero de visita: " + numerovis + " \nFecha: "+ fecha +"\n\n");
            }
            viewer.agregarConsulta(string.toString());
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
            	if (rs != null) rs.close();
                if (stm != null) stm.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
