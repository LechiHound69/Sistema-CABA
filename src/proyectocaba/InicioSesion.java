package proyectocaba;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InicioSesion {
    
    private static HashMap<String, String> credenciales = new HashMap<>(); //
    private static final String ARCHIVO_CREDENCIALES = "data/credenciales.txt";
    private static HashMap<String, String[]> datos_usu = new HashMap<>();
    private static final String ARCHIVO_DATOS = "data/datos_usuarios.txt";
    public static HashMap<String, String> auxiliar_nombre_ID = new HashMap<>();
    
    //private String usuario; 
    //private String contraseña;
    
    public static boolean autenticar(String usuario, String contrasena){
        try{
            cargarCredenciales();
            String hash_intento = generarHash(contrasena);
            String hash_guardado = credenciales.get(usuario);
            return hash_intento.equals(hash_guardado);
        } catch (Exception err){
            System.out.println("Error de autenticación, por favor vuelva a intentar");
            return false;
        }
    }
    
    public static Usuario iniciar(String ID_usu){
        String nombre;
        String tipo_usuario;
        String[] datos_usuario;
        try{
            //cargarDatosUsuario();
            datos_usuario = datos_usu.get(ID_usu);
            int size = datos_usuario.length;
            for (int i = 0; i < size; i++){datos_usuario[i] = datos_usuario[i].trim();}
            //String ID_usu = datos_usuario[0];
            nombre = datos_usuario[1];
            tipo_usuario = datos_usuario[2];
        } catch (Exception err){
            System.out.println("Error al cargar los datos del usuario, por favor vuelva a intentar");
            return null;
        }
        switch (tipo_usuario){
            case "externo" -> {
                String ID_politica = datos_usuario[3];
                UsuarioExterno user = new UsuarioExterno(ID_usu, nombre, tipo_usuario, ID_politica);
                return user;
            }
            
            case "interno" -> {
                String departamento = datos_usuario[3];
                UsuarioInterno user = new UsuarioInterno(ID_usu, nombre, tipo_usuario, departamento);
                return user;
            }
            
            default -> {return null;}
        }
        
    }
    
    public static String generarHash(String contrasena){
        int hash = contrasena.hashCode();
        return Integer.toString(hash);
    }
    
    private static void cargarCredenciales(){
        String linea;
        try(BufferedReader login = new BufferedReader(new FileReader(ARCHIVO_CREDENCIALES))){
            while ((linea = login.readLine()) != null){
                String[] partes = linea.split(",");
                int size = partes.length;
                for (int i = 0; i < size; i++){partes[i] = partes[i].trim();}
                String usu = partes[0];
                String contrasena_guardada = partes[1];
                credenciales.put(usu, contrasena_guardada);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    public static void cargarDatosUsuario(){
        //El flujo de datos es: ID_Usuario [0], nombre [1], tipo_usuario [2], ID_politica ó departamento [3]
        String linea;
        try(BufferedReader data = new BufferedReader(new FileReader(ARCHIVO_DATOS))){
            while ((linea = data.readLine()) != null){
                String[] datos = linea.split(",");
                int size = datos.length;
                for (int i = 0; i < size; i++){datos[i] = datos[i].trim();}
                String ID_usu = datos[0];
                String nombre = datos[1];
                auxiliar_nombre_ID.put(nombre.toLowerCase(), ID_usu);
                datos_usu.put(ID_usu, datos);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }

    public static HashMap<String, String[]> getDatos_usu() {
        return datos_usu;
    }
    
    public static String[] getLista_datos_usu(String nombre_usu){
        try{
            String ID_usu = auxiliar_nombre_ID.get(nombre_usu);
            
            return datos_usu.get(ID_usu);
        } catch (Exception err){
            System.out.println("Error fatal al cargar los datos del usuario");
            return null;
        }
    }

    public static String getARCHIVO_DATOS() {
        return ARCHIVO_DATOS;
    }

    public static String getARCHIVO_CREDENCIALES() {
        return ARCHIVO_CREDENCIALES;
    }


    
    
}