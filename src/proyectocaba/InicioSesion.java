
package proyectocaba;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InicioSesion {
    
    private HashMap<String, String> credenciales; //
    private String ARCHIVO_CREDENCIALES;
    private HashMap<String, String[]> datos_usu;
    private String ARCHIVO_DATOS;
    private String usuario; 
    private String contraseña;
    
    public boolean autenticar(String usuario, String contrasena){
        String hash_intento = generarHash(contrasena);
        String hash_guardado = credenciales.get(usuario);
        return hash_intento.equals(hash_guardado);
    }
    
    public Usuario iniciar(String ID_usuario){
        
        String[] datos_usuario = datos_usu.get(ID_Usuario);
        for (int i = 0; i < datos_usuario.lenght(); i++){datos_usuario[i].trim()}
        String ID_usuario = datos_usuario[0];
        String tipo_usuario = datos_usuario[1];
        String nombre = datos_usuario[2];
        
        
        if (tipo_usuario.equals("Externo")){
            String tipo_contrato = datos_usuario[3].trim();
            UsuarioExterno user = new UsuarioExterno(ID_usuario, nombre, tipo_usuario, tipo_contrato);
            return user;
        } else if(tipo_usuario.equals("Interno")){
            String departamento = datos_usuario[3].trim();
            UsuarioInterno user = new UsuarioInterno(ID_usuario, nombre, tipo_usuario, departamento);
            return user;
        } else {
            return null;
        }
    }
    
    private String generarHash(String contrasena){
        int hash = contrasena.hashCode();
        return Integer.toString(hash);
    }
    
    public void cargarCredenciales(){
        String linea;
        try(BufferedReader login = new BufferedReader(new FileReader(ARCHIVO_CREDENCIALES))){
            while ((linea = login.readLine()) != null){
                String[] partes = linea.split(",");
                String usu = partes[0].trim();
                String contrasena_guardada = partes[1].trim();
                this.credenciales.put(usu, contrasena_guardada);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    public void cargarDatosUsuario(){
        //El flujo de datos es: ID_Usuario [0], nombre [1], tipo_usuario [2], tipo_contrato ó departamento [3]
        String linea;
        try(BufferedReader data = new BufferedReader(new FileReader(ARCHIVO_DATOS))){
            while ((linea = login.readLine()) != null){
                String[] datos = linea.split(",");
                for (int i = 0; i < datos.lenght(); i++){datos[i].trim()}
                String ID_usu = datos[0].trim;
                this.datos_usu.put(ID_usu, datos);
            } catch (IOException err){
                err.printStackTrace();
            }
        }
    }
    
    
}