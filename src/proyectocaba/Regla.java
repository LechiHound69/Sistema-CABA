package proyectocaba;
import java.util.ArrayList;
import java.time.LocalDate;
/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;*/

public class Regla {
    
    //private int ID_regla;
    public String conjunto_datos;
    public String tabla_permitida;
    public LocalDate fecha_vencimiento;
    public ArrayList<String> columnas_permitidas = new ArrayList<>();

    /*public String getConjunto_datos() {
        return conjunto_datos;
    }*/
    

    public Regla(String conjunto_datos, String tabla_permitida, LocalDate fecha_vencimiento, ArrayList<String> columnas_permitidas) {
        //this.ID_regla = ID_regla;
        this.conjunto_datos = conjunto_datos.trim();
        this.tabla_permitida = tabla_permitida.trim();
        this.fecha_vencimiento = fecha_vencimiento;
        this.columnas_permitidas = columnas_permitidas;
    }
    
    
    //vamos a hacer el fabricador que obtenga las reglas de una política a través de un archivo de nombre igual al
    //ID_Politica uwu
}