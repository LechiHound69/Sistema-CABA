package proyectocaba;
//import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;


public class UsuarioInterno extends Usuario{
    
    private static Scanner entry = new Scanner(System.in);
    private String departamento;

    public UsuarioInterno(String ID_Usuario, String nombre, String tipo_de_usuario, String departamento) {
        super(ID_Usuario, nombre, tipo_de_usuario);
        this.departamento = departamento;
    }
    
    private void crearRegla(){
        String usuario;
        String ARCHIVO_POL = null;
        String nuevo_conjunto;
        String nueva_tabla;
        LocalDate fecha_vencimiento = LocalDate.of(1970, 1, 1);
        ArrayList<String> nuevas_columnas = new ArrayList<>();
        boolean flag = false;
        
        while (!flag){
            System.out.println("Ingrese el nombre del usuario para crear regla (o 'salir' para cancelar): ");
            usuario = entry.nextLine().trim();
            
            if (InicioSesion.auxiliar_nombre_ID.get(usuario) != null){
                System.out.println("Usuario encontrado.");
                String[] datos = InicioSesion.getLista_datos_usu(usuario);
                if (datos[2].equalsIgnoreCase("interno")){
                    System.out.println("Error: No se puede crear una regla a un usuario interno. Por favor intente de nuevo");
                    continue;
                }
                ARCHIVO_POL = datos[3].trim() + ".txt";
                flag = true;
            } else if (usuario.trim().equalsIgnoreCase("salir")) {
                return;
            } else {
                System.out.println("Error: Usuario no encontrado. Por favor intente de nuevo");
                flag = false;
            }
            
        }
        
        System.out.println("Ingrese el nombre del conjunto de datos al que se dará acceso (o 'salir' para cancelar): ");
        nuevo_conjunto = entry.nextLine().trim().toLowerCase();
        if (nuevo_conjunto.trim().equalsIgnoreCase("salir")){return;}
        
        System.out.println("Ingrese el nombre de la tabla del conjunto de datos al que se dará acceso (o 'salir' para cancelar): ");
        nueva_tabla = entry.nextLine().trim().toLowerCase();
        if (nueva_tabla.trim().equalsIgnoreCase("salir")){return;}
        
        flag = false;
        while(!flag){
            System.out.println("Ingrese el nombre de la columna a la que se dará acceso ('fin' para finalizar el ingreso de columnas, o 'salir' para cancelar): ");
            String columna = entry.nextLine().trim().toLowerCase();
            
            switch(columna){
                case "fin" -> {
                    if (nuevas_columnas.isEmpty()){
                        System.out.println("Error: Ingrese al menos una columna antes de finalizar");
                        continue;
                    } else {
                        System.out.println("Columnas guardadas con éxito");
                        flag = true;
                    }
                }
                
                case "salir" -> {return;}
                
                default -> {continue;}
                
            }
            nuevas_columnas.add(columna);
        }
        
        flag = false;
        while (!flag){
            System.out.print("Ingrese la fecha máxima de acceso (FORMATO: YYYY-MM-DD, o 'salir' para cancelar): ");
            String fecha = entry.nextLine().trim();

            try {
                
                fecha_vencimiento = LocalDate.parse(fecha);
                flag = true;

            } catch (DateTimeParseException err) {
                System.out.println("❌ Error: El formato de fecha es incorrecto o la fecha no es válida. Intente de nuevo.");
                flag = false;
            }
        
        }
        
        String linea = nuevo_conjunto + "," + nueva_tabla + "," + fecha_vencimiento.toString();
        for (String columna : nuevas_columnas){
            linea = linea + "," + columna;
        }
        
        try(BufferedWriter crear = new BufferedWriter(new FileWriter(ARCHIVO_POL, true))){
            crear.write(linea.trim());
            crear.newLine();
            System.out.println("Regla creada con éxito");
        } catch (IOException err){
            err.printStackTrace();
            System.out.println("Error de I/O: No se pudo escribir en el archivo");
        }
        
    }
    
    private void crearUsuario(){
        boolean flag = false;
        String nuevo_ID = null;
        String nombre_nuevo = null;
        String tipo = "";
        String departamento_nuevo;
        String linea_nueva = "";
        String nuevo_ID_pol = "POL-";
        String nueva_contrasena;
        
        int siguiente_usu = InicioSesion.auxiliar_nombre_ID.size() + 1;
        String secuencia = String.format("%0" + 4 + "d", siguiente_usu);
        
        while (!flag){
            System.out.println("Ingrese el nombre del nuevo usuario (o 'fin' para cancelar): ");
            nombre_nuevo = entry.nextLine().trim();

            if (InicioSesion.auxiliar_nombre_ID.get(nombre_nuevo.toLowerCase()) != null){
                System.out.println("Error: El usuario ya existe");
                flag = false;
            } else if (nombre_nuevo.trim().equalsIgnoreCase("fin")) {
                return;
            } else {
                flag = true;
            }
            
        }
        
        flag = false;
        while (!flag){
            System.out.println("Ingrese el tipo de usuario (Interno, externo, o 'fin' para cancelar): ");
            tipo = entry.nextLine().trim().toLowerCase();
            
            switch(tipo.trim().toLowerCase()){
                
                case "fin" -> {return;}
                    
                case "interno" -> {
                    flag = true;
                    nuevo_ID = "UIN-" + secuencia;
                    //nuevo_ID = nuevo_ID + secuencia;
                    System.out.println("Ingrese el departamento al que pertenece el nuevo usuario (o 'fin' para cancelar): ");
                    departamento_nuevo = entry.nextLine().trim();
                    if (departamento_nuevo.trim().equalsIgnoreCase("fin")){return;}
                    linea_nueva = nuevo_ID + "," + nombre_nuevo + "," + tipo + "," + departamento_nuevo;
                }
                    
                case "externo" -> {
                    flag = true;
                    nuevo_ID = "UEX-" + secuencia;
                    //nuevo_ID = nuevo_ID + secuencia;
                    nuevo_ID_pol = PoliticaDeAcceso.generarIDPolitica();
                    linea_nueva = nuevo_ID + "," + nombre_nuevo + "," + tipo + "," + nuevo_ID_pol;
                }
                
                default -> System.out.println("Opción inválida, por favor intente otra vez.");
            }
        }
        
        System.out.println("Ingrese la contraseña del nuevo usuario: ");
        nueva_contrasena = entry.nextLine().trim();
        nueva_contrasena = InicioSesion.generarHash(nueva_contrasena);
        
        String ARCHIVO = InicioSesion.getARCHIVO_DATOS();
        try(BufferedWriter crear = new BufferedWriter(new FileWriter(ARCHIVO, true))){
            crear.write(linea_nueva.trim());
            crear.newLine();
            System.out.println("Usuario creado con éxito");
        } catch (IOException err){
            err.printStackTrace();
            System.out.println("Error de I/O: No se pudo escribir en el archivo");
        }
        
        String ARCHIVO_CREDENCIALES = InicioSesion.getARCHIVO_CREDENCIALES();
        try(BufferedWriter guardar_credenciales = new BufferedWriter(new FileWriter(ARCHIVO_CREDENCIALES, true))){
            guardar_credenciales.write(nuevo_ID + "," + nueva_contrasena);
            guardar_credenciales.newLine();
            System.out.println("Credenciales de acceso creadas exitosamente");
        } catch (IOException err){
            err.printStackTrace();
        }
            
        if (tipo.equalsIgnoreCase("externo")){
            //falta poner la ubicacion de archivo
            File nueva_pol = new File(nuevo_ID_pol + ".txt");
            try {
                if (nueva_pol.createNewFile()){
                    System.out.println("Política de usuario nuevo creada exitosamente");
                }
                
            } catch (IOException err){
                err.printStackTrace();
            }
            
        }
        
    }
    
    private static void verLogs(){
        String linea;
        try(BufferedReader logs = new BufferedReader(new FileReader(Usuario.ARCHIVO_LOGS))){
            while ((linea = logs.readLine()) != null){
                System.out.println(linea);
            }
        } catch (IOException err){
            err.printStackTrace();
        }
    }
    
    private void verInfoUsu(){
        while (true){
            System.out.println("Ingrese el nombre del usuario a consultar datos (o 'fin' para cancelar): ");
            String usu = entry.nextLine().trim();

            if (InicioSesion.auxiliar_nombre_ID.get(usu.toLowerCase()) != null){
                String[] datos = InicioSesion.getLista_datos_usu(usu);
                String linea = String.join(",", datos);
                System.out.println("Datos del usuario ingresado: " + linea);
            } else if (usu.trim().equalsIgnoreCase("fin")) {
                return;
            } else {
                System.out.println("Error: El usuario no existe.");
            }
            
        }
    }
    
    //private void otorgarAcceso(){}
    
    //private void revocarAcceso(){}
    
    @Override
    public String verInfo(){
        return "Datos del Usuario:\n" + "|Id: " + this.ID_Usuario + "\n" + "|Nombre: " + this.nombre + "\n" + "|Tipo de usuario: " + this.tipo_de_usuario + "\n" + "|Departamento del usuario: " + this.departamento + "\n";
    }
    
    @Override
    public void verMenu(){
        
        while (true){
            System.out.println("Bienvenido, " + this.nombre + ". Por favor elija una opción: ");
            System.out.println("Opción 1: Ver logs de acceso");
            System.out.println("Opción 2: Crear usuario");
            System.out.println("Opción 3: Crear regla para un usuario");
            System.out.println("Opción 4: Ver info de un usuario específico");
            System.out.println("Opción 5: Ver info del usuario logueado");
            System.out.println("Opción 6: Cerrar sesión");
            int opcion = entry.nextInt();

            switch (opcion) {
                case 1 -> verLogs();
                case 2 -> this.crearUsuario();
                case 3 -> this.crearRegla();
                case 4 -> this.verInfoUsu();
                case 5 -> this.verInfo();
                case 6 -> {return;}
                default -> {
                    System.out.println("Opción inválida. Elija otra opción:");
                }
            }
        }
    }

}
