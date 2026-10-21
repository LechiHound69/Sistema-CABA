
package proyectocaba;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

public class PoliticaDeAcceso {
    
    private String ID_Politica;
    //private String ARCHIVO_POLITICA;
    private ArrayList<Regla> politica = new ArrayList<>();
    
    public PoliticaDeAcceso(int ID_Politica){
        this.ID_Politica = ID_Politica;
        cargarPolitica();
    }
    
    public PoliticaDeAcceso(int ID_Politica, ArrayList<Regla> politica){
        this.ID_Politica = ID_Politica;
        this.politica = politica;
    }
    
    public boolean evaluar(Regla regla){
        return true;
    }

    /*public ArrayList<Regla> getPolitica() {
        return politica;
    } */
    
    public void cargarPolitica(){
        String linea;
        try(BufferedReader pol = new BufferedReader(new FileReader(this.ID_Politica))){
            Regla regla;
            while ((linea = pol.readLine()) != null){
                String[] partes = linea.split(",");
                for (int i = 0; i < partes.lenght(); i++){partes[i].trim()}
                int ID_regla = Integer.parseInt(partes[0]);
                String conjunto_datos = partes[1];
                LocalDate fecha = LocalDate.parse(partes[2]);
                ArrayList<String> tablas = new ArrayList<>();
                for (int j = 3; j < partes.lenght(); j++){tablas.add(partes[j])}
                regla = new Regla(ID_regla, conjunto_datos, fecha, tablas);
                this.politica.add(regla);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    private void crearRegla(){}
    
    private void eliminarRegla(){}
    
    private void editarRegla(){}
}