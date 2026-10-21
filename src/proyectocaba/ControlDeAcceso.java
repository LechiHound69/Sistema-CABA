
package proyectocaba;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ControlDeAcceso {
    
    private String ARCHIVO_HASH;
    private HashMap<String, String> politicas_guardadas;

    public ControlDeAcceso() {
        this.politicas_guardadas = new HashMap<>();
    }
    
    public PoliticaDeAcceso obtenerPolitica(String ID_Usuario){
        return this.politicas_guardadas.get(ID_Usuario);
    }
    
    public boolean gestionarAcceso(String ID_Usuario){
        
        PoliticaDeAcceso politica_usuario = politicas_guardadas.get(ID_Usuario);
        
        if (politica_usuario == null){
            return false;
        }
        
        return true;
    }
    
    public void cargarHash(){
        String linea;
        try (BufferedReader hash = new BufferedReader(new FileReader(ARCHIVO_HASH))){
            while ((linea = hash.readLine()) != null){
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.lenght(); i++){datos[i].trim()}
                String ID_usu = partes[0].trim;
                String ID_pol = partes[1].trim;
                this.politicas_guardadas.put(ID_usu, ID_pol);
            }
        }
    }
}