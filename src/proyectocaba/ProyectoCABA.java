package proyectocaba;
import java.util.Scanner;

public class ProyectoCABA {
    private Usuario usuario_actual;
    private static Scanner entry = new Scanner(System.in);

    public static void main(String[] args) {
        boolean login_aprobado = false;
        String nombre;
        String ID = null;
        System.out.println("Bienvenido, por favor inicie sesion.");
        while(!login_aprobado){
            try{
                System.out.println("Ingrese su nombre de usuario (o 'fin' para salir): ");
                nombre = entry.nextLine().trim().toLowerCase();
                if (nombre.equalsIgnoreCase("fin")){return;}
                InicioSesion.cargarDatosUsuario();
                ID = InicioSesion.auxiliar_nombre_ID.get(nombre.toLowerCase());
                String contrasena;
                if (ID != null){
                    System.out.println("Ingrese su contraseña: ");
                    contrasena = entry.nextLine();
                    
                } else {
                    System.out.println("Error: El usuario ingresado no existe. Intente nuevamente");
                    continue;
                }
            } catch (Exception err){
                System.out.println("Por favor ingrese una entrada válida");
                continue;
            }

            login_aprobado = InicioSesion.autenticar(ID, contrasena);
            if (!login_aprobado){System.out.println("Contraseña incorrecta. Por favor vuelva a intentar");}
        }
        
        Usuario usuario_activo = InicioSesion.iniciar(ID);
        if (usuario_activo == null){
            System.out.println("Error fatal al cargar los datos.");
            return;
        }
        usuario_activo.verMenu();
    }
       
}