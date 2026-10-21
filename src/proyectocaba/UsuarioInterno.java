
package proyectocaba;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class UsuarioInterno extends Usuario{
    
    private String departamento;

    public UsuarioInterno(String ID_Usuario, String nombre, String tipo_de_usuario, String departamento) {
        super(ID_Usuario, nombre, tipo_de_usuario);
        this.departamento = departamento;
    }
    
    private void crearPolitica(){}
    
    private void crearUsuario(){
        String ARCHIVO_CREDENCIALES = "";
        try(BufferedWriter crear = new BufferedWriter(new FileWriter(ARCHIVO_CREDENCIALES))){
        } catch (IOException err){
        }
    }
    
    private void modificarUsuario(){}
    
    private void otorgarAcceso(){}
    
    private void revocarAcceso(){}
    
    @Override
    public String verInfo(String ID_Usuario, String nombre, String tipo_de_usuario){
        return "Datos del Usuario:\n" + "|Id: " + this.ID_Usuario + "\n" + "|Nombre: " + this.nombre + "\n" + "|Tipo de usuario: " + this.tipo_de_usuario + "\n" + "|Departamento del usuario: " + this.departamento + "\n";
    }
    
}
