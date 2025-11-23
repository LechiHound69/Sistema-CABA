package proyectocaba;
/*import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;*/
import java.time.LocalDate;

public class ControlDeAcceso {
    
    //private static String ARCHIVO_HASH;
    //private static HashMap<String, String> politicas_guardadas;
    //public static int tamano_hash;
    private static LocalDate hoy = LocalDate.now();
    
    /*public static String obtenerPolitica(String ID_Usuario){
        return politicas_guardadas.get(ID_Usuario);
    } */
    
    /*public static boolean gestionarAcceso(String ID_Usuario){
        
        String id_politica = politicas_guardadas.get(ID_Usuario);
        
        if (id_politica == null){
            return false;
        }
        
        return true;
    } */
    
    public static boolean evaluar(Regla regla){
        try{
            LocalDate fecha_max = regla.fecha_vencimiento;
            return hoy.isBefore(fecha_max) || hoy.isEqual(fecha_max);
        } catch (Exception err) {
            System.out.println("Error fatal al leer regla, regla no válida");
            return false;
        }
    }
    
    /*public static void cargarHash(){
        String linea;
        try (BufferedReader hash = new BufferedReader(new FileReader(ARCHIVO_HASH))){
            while ((linea = hash.readLine()) != null){
                String[] partes = linea.split(",");
                int size = partes.length;
                for (int i = 0; i < size; i++){partes[i] = partes[i].trim();}
                String ID_usu = partes[0];
                String ID_pol = partes[1];
                politicas_guardadas.put(ID_usu, ID_pol);
            }
            tamano_hash = politicas_guardadas.size();
        } catch (IOException err){
            err.printStackTrace();
        }
    }  

    public static String getARCHIVO_HASH() {
        return ARCHIVO_HASH;
    } */
    
}