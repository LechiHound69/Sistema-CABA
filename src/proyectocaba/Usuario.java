
package proyectocaba;


public abstract class Usuario {
    
    protected String ID_Usuario;
    public String tipo_de_usuario;
    protected String nombre;

    public Usuario(String ID_Usuario, String nombre, String tipo_de_usuario) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.tipo_de_usuario = tipo_de_usuario;
    }
    
    public abstract String verInfo(String ID_Usuario, String nombre , String tipo_de_usuario);
    
    //public abstract void menu();
}
