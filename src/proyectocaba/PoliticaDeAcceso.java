
package proyectocaba;
import java.util.ArrayList;


public class PoliticaDeAcceso {
    
    private int ID_Politica;
    private ArrayList<Regla> politica = new ArrayList<>();
    
    public boolean evaluar(Regla regla){
        return true;
    }

    public ArrayList<Regla> getPolitica() {
        return politica;
    }
    
    private void crearRegla(){}
    
    private void eliminarRegla(){}
    
    private void editarRegla(){}
}
