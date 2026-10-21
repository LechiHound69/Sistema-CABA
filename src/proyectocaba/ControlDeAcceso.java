
package proyectocaba;
import java.util.HashMap;


public class ControlDeAcceso {
    
    private String version_sistema;
    private HashMap<String, PoliticaDeAcceso> politicas_guardadas;

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
}
