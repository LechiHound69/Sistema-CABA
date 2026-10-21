
package proyectocaba;
import java.util.ArrayList;
import java.time.LocalDate;

public class Regla {
    
    private int ID_regla;
    public String conjunto_datos;
    private LocalDate fecha_vencimiento;
    public ArrayList<String> tablas_permitidas = new ArrayList<>();

    public String getConjunto_datos() {
        return conjunto_datos;
    }
    

    public Regla(String conjunto_datos, LocalDate fecha_vencimiento) {
        this.conjunto_datos = conjunto_datos;
        this.fecha_vencimiento = fecha_vencimiento;
    }
    
    
}
