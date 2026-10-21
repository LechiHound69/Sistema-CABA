package proyectocaba;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Usuario {
    
    protected String ID_Usuario;
    public String tipo_de_usuario;
    protected String nombre;
    public static final String ARCHIVO_LOGS = "data/logs.txt";

    public Usuario(String ID_Usuario, String nombre, String tipo_de_usuario) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.tipo_de_usuario = tipo_de_usuario;
    }

    public static void crearLog(String accion, String ID_usu, String resultado){
        LocalDateTime timestamp = LocalDateTime.now();
        String fecha_hora = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        String linea = fecha_hora + "|" + ID_usu + "|" + accion + "|" + resultado;
        
        try(BufferedWriter add_log = new BufferedWriter(new FileWriter(ARCHIVO_LOGS, true))){
            add_log.write(linea.trim());
            add_log.newLine();
        } catch (IOException err){
            err.printStackTrace();
            System.out.println("Error de I/O: No se pudo escribir en el archivo de logs");
        }
    }
    
    public abstract void verInfo();
    
    public abstract void verMenu();    
    
}