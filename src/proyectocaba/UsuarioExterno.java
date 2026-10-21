
package proyectocaba;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioExterno extends Usuario{
    
    private Scanner entry = new Scanner(System.in);
    private String tipo_contrato;
    private boolean token;
    private String nombre_conjunto;
    private Regla regla_solicitada;

    public UsuarioExterno(String ID_Usuario, String nombre, String tipo_de_usuario, String tipo_contrato) {
        super(ID_Usuario, nombre, tipo_de_usuario);
        this.tipo_contrato = tipo_contrato;
    }
    
        
    public void solicitarAcceso(ControlDeAcceso autoridad){
        
        boolean verificacion_politica = autoridad.gestionarAcceso(ID_Usuario);
        if (verificacion_politica){
            
            PoliticaDeAcceso politica = autoridad.obtenerPolitica(ID_Usuario);

            ArrayList<Regla> lista_reglas = politica.getPolitica();

            System.out.println("\n--- Reglas de acceso disponibles: ---");

            for (int i =  0; i < lista_reglas.size(); i++){
                Regla regla = lista_reglas.get(i);
                System.out.println("Opción " + i + ": " + (i + 1) + ". " + regla.getConjunto_datos());
            }

            System.out.println("Elija una opcion de regla para solicitar el acceso: ");

            int opcion;

            while(true){

                try {
                    opcion = entry.nextInt();
                } catch (Exception err){
                    System.out.println("Por favor ingrese una opción válida.");
                    entry.nextLine();
                    continue;
                }
                
                break;
            }
            
            regla_solicitada = lista_reglas.get(opcion);
            token = politica.evaluar(regla_solicitada);
            nombre_conjunto = regla_solicitada.conjunto_datos;
            
        }
        
        
        
    }
    
    private void consultarDatos(){
        if (token){
            System.out.println("Acceso aprobado, cargando datos...");
            Recurso recurso_cargado = new Recurso();
            //ArrayList<String> tablas_permitidas = regla_solicitada.tablas_permitidas;
            
            recurso_cargado.mostrarDatos(token, nombre_conjunto, regla_solicitada.tablas_permitidas);
        }
    }
    
    
    @Override
    public String verInfo(String ID_Usuario, String nombre, String tipo_de_usuario){
        return "Datos del Usuario:\n" + "|Id: " + this.ID_Usuario + "\n" + "|Nombre: " + this.nombre + "\n" + "|Tipo de usuario: " + this.tipo_de_usuario + "\n" + "|Tipo de contrato actual: " + this.tipo_contrato + "\n";
    }
    
}
