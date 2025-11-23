package proyectocaba;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class PoliticaDeAcceso {
    
    private String ID_Politica;
    private ArrayList<Regla> politica = new ArrayList<>();
    private static final String RUTA_POLITICAS = "data/politicas/";
    
    public PoliticaDeAcceso(String ID_Politica){
        this.ID_Politica = ID_Politica;
    }

    public ArrayList<Regla> getPolitica() {
        return politica;
    }
    
    public void cargarPolitica(){
        String linea;
        String ruta = RUTA_POLITICAS + this.ID_Politica + ".txt";
        try(BufferedReader pol = new BufferedReader(new FileReader(ruta))){
            Regla regla;
            while ((linea = pol.readLine()) != null){
                String[] partes = linea.split(",");
                int size = partes.length;
                for (int i = 0; i < size; i++){partes[i] = partes[i].trim();}
                //el flujo de la regla es ID, conjunto, tabla, fecha y columnas
                //int ID_regla = Integer.parseInt(partes[0]);
                String conjunto_datos = partes[0];
                String tabla = partes[1];
                LocalDate fecha = LocalDate.parse(partes[2]);
                ArrayList<String> columnas = new ArrayList<>();
                for (int j = 3; j < size; j++){columnas.add(partes[j]);}
                regla = new Regla(conjunto_datos, tabla, fecha, columnas);
                this.politica.add(regla);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    public static String generarIDPolitica(){
        File carpeta_pol = new File(RUTA_POLITICAS);
        
        File[] archivos = carpeta_pol.listFiles();
        
        int siguiente_pol = 1;
        
        if (archivos != null){
            int size = archivos.length;
            siguiente_pol = size + 1;
        }
        
        String secuencia = String.format("%0" + 3 + "d", siguiente_pol);
        
        return "POL-" + secuencia;
        
    }

    public static String getRUTA_POLITICAS() {
        return RUTA_POLITICAS;
    }
    
}