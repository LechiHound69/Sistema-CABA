package proyectocaba;
import java.util.ArrayList;
import java.util.Scanner;

public class UsuarioExterno extends Usuario{
    
    public String ID_politica;
    private static Scanner entry = new Scanner(System.in);

    public UsuarioExterno(String ID_Usuario, String nombre, String tipo_de_usuario, String ID_politica) {
        super(ID_Usuario, nombre, tipo_de_usuario);
        this.ID_politica = ID_politica;
    }
    
        
    public void solicitarAcceso(){
        boolean token;
                    
        PoliticaDeAcceso politica = new PoliticaDeAcceso(this.ID_politica);
            
        politica.cargarPolitica();
        ArrayList<Regla> lista_reglas = politica.getPolitica();

        int opcion;
        boolean flag = false;

        while(!flag){

            try {
                System.out.println("\n--- Reglas de acceso disponibles: ---");

                for (int i =  0; i < lista_reglas.size(); i++){
                    Regla regla = lista_reglas.get(i);
                    System.out.println("Opción " + i + ": " + regla.conjunto_datos + ", " + regla.tabla_permitida);
                }

                System.out.println("Elija una opcion de regla para solicitar el acceso (o cualquier tecla para volver): ");
        
                opcion = entry.nextInt();
                entry.nextLine();
                
                Regla regla_solicitada = lista_reglas.get(opcion);
                token = ControlDeAcceso.evaluar(regla_solicitada);

                if (token){
                    Recurso.setNombre_conjunto_datos(regla_solicitada.conjunto_datos.trim());
                    Recurso.setTabla(regla_solicitada.tabla_permitida.trim());
                    int size = regla_solicitada.columnas_permitidas.size();
                    for (int i = 0; i < size; i++){regla_solicitada.columnas_permitidas.set(i, regla_solicitada.columnas_permitidas.get(i).trim());}
                    Recurso.setColumnas_permitidas(regla_solicitada.columnas_permitidas);

                    Recurso.cargarDatos();
                    Recurso.mostrarDatos();
                    flag = true;
                    Usuario.crearLog("Solicitud de acceso", this.ID_Usuario, "Aprobado");
                } else {
                    System.out.println("Error: La regla solicitada ya se venció, por favor intente con otra.");
                    Usuario.crearLog("Solicitud de acceso", this.ID_Usuario, "Denegado");
                }
                
            } catch (Exception err){
                entry.nextLine();
                return;
            }
                
        }
        
    }
    
    /*private void consultarDatos(){
        if (token){
            System.out.println("Acceso aprobado, cargando datos...");
            Recurso recurso_cargado = new Recurso();
            //ArrayList<String> columnas_permitidas = regla_solicitada.columnas_permitidas;
            
            recurso_cargado.mostrarDatos(token);
        }
    }*/
    
    
    @Override
    public void verInfo(){
        System.out.println("Datos del Usuario:\n" + "|Id: " + this.ID_Usuario + "\n" + "|Nombre: " + this.nombre + "\n" + "|Tipo de usuario: " + this.tipo_de_usuario + "\n");
    }
    
    @Override
    public void verMenu(){
        
        while (true){
            try{
                System.out.println();
                System.out.println("Bienvenido, " + this.nombre + ". Por favor elija una opción: ");
                System.out.println("Opción 1: Solicitar acceso a un recurso");
                System.out.println("Opción 2: Ver info del usuario logueado");
                System.out.println("Opción 3: Cerrar sesión");
                int opcion = entry.nextInt();
                entry.nextLine();

                switch (opcion) {
                    case 1 -> this.solicitarAcceso();
                    case 2 -> this.verInfo();
                    case 3 -> {return;}
                    default -> {
                        System.out.println("Opción inválida. Elija otra opción:");
                    }
                }
            } catch (java.util.InputMismatchException err){
                entry.nextLine();
                System.out.println("Por favor ingrese un valor válido. Se esperaba un número");
            }
        }
    }
    
}