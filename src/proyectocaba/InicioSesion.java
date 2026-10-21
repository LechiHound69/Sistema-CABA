
package proyectocaba;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InicioSesion {
    
    private HashMap<String, String> credenciales;
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
    
    public Usuario iniciar(){
        String[] datos_usuario = this.credenciales.get(usuario).split(",");
        String ID_usuario = datos_usuario[0].trim();
        String tipo_usuario = datos_usuario[1].trim();
        String nombre = datos_usuario[2].trim();
        
        
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
                String usu = partes[0];
                String contrasena_guardada = partes[1];
                this.credenciales.put(usu, contrasena_guardada);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    public void cargarDatosUsuario(){
    }
    
    
}
