package proyectocaba;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Recurso {
    
    private static String nombre_conjunto_datos;
    private static String tabla;
    private static ArrayList<String> columnas_permitidas;
    private static ArrayList<ArrayList<String>> datos_tabla = new ArrayList<>();

    /*public Recurso(String nombre_conjunto_datos, String tabla, ArrayList<String> columnas_permitidas) {
        this.nombre_conjunto_datos = nombre_conjunto_datos;
        this.tabla = tabla;
        this.columnas_permitidas = columnas_permitidas;
    } */
    
    public static void cargarDatos(){
        String linea;
        String ruta = "data/recursos/" + nombre_conjunto_datos.trim() + "/" + tabla.trim() + ".txt";
        try(BufferedReader data = new BufferedReader(new FileReader(ruta))){
            while ((linea = data.readLine()) != null){
                String[] partes = linea.trim().split("\\|");
                ArrayList<String> fila = new ArrayList<>();
                
                for (String parte : partes){
                    fila.add(parte.trim());
                }
                
                datos_tabla.add(fila);
                
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    public static void mostrarDatos(){
        try{
            ArrayList<Integer> indices = new ArrayList<>();
            ArrayList<String> fila_principal = datos_tabla.get(0);
            String columna_guardada;
            int size = fila_principal.size();
            
            for (String columna_permitida : columnas_permitidas){
                for (int i = 0; i < size; i++){
                    columna_guardada = fila_principal.get(i);
                    if(columna_permitida.trim().equalsIgnoreCase(columna_guardada)){
                        indices.add(i);
                    }
                        
                }
            }
            
            for (ArrayList<String> fila : datos_tabla){
                for (int index : indices){
                    System.out.print(fila.get(index) + "  |  ");
                }
                System.out.println();
            }
        } catch (Exception err){
            System.out.println("Error fatal al mostrar los datos, posible matriz vacía");
            return;
        }
    }

    public static void setNombre_conjunto_datos(String nombre_conjunto_datos) {
        Recurso.nombre_conjunto_datos = nombre_conjunto_datos;
    }

    public static void setTabla(String tabla) {
        Recurso.tabla = tabla;
    }

    public static void setColumnas_permitidas(ArrayList<String> columnas_permitidas) {
        Recurso.columnas_permitidas = columnas_permitidas;
    }
    
    
    
}