
package proyectocaba;
import java.util.ArrayList;


public class PoliticaDeAcceso {
    
    private int ID_Politica;
    private ArrayList<Regla> politica = new ArrayList<>();
    
    public PoliticaDeAcceso(int ID_Politica){
        this.ID_Politica = ID_Politica;
    }
    
    public PoliticaDeAcceso(int ID_Politica, ArrayList<Regla> politica){
        this.ID_Politica = ID_Politica;
        this.politica = politica;
    }
    
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