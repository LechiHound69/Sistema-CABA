
package proyectocaba;
import java.util.ArrayList;
import java.time.LocalDate;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Regla {
    
    private int ID_regla;
    public String conjunto_datos;
    public LocalDate fecha_vencimiento;
    public ArrayList<String> tablas_permitidas;

    public String getConjunto_datos() {
        return conjunto_datos;
    }
    

    public Regla(int ID_regla, String conjunto_datos, LocalDate fecha_vencimiento, ArrayList<String> tablas_permitidas) {
        this.ID_regla = ID_regla;
        this.conjunto_datos = conjunto_datos;
        this.fecha_vencimiento = fecha_vencimiento;
        this.tablas_permitidas = tablas_permitidas;
    }
    
    
    //vamos a hacer el fabricador que obtenga las reglas de una política a través de un archivo de nombre igual al
    //ID_Politica uwu
}
