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
            try{
                System.out.println("Ingrese el nombre del usuario para crear regla (o 'salir' para cancelar): ");
                usuario = entry.nextLine().trim();
                
                if (InicioSesion.auxiliar_nombre_ID.get(usuario) != null){
                    System.out.println("Usuario encontrado.");
                    String[] datos = InicioSesion.getLista_datos_usu(usuario);
                    if (datos[2].equalsIgnoreCase("interno")){
                        System.out.println("Error: No se puede crear una regla a un usuario interno. Por favor intente de nuevo");
                        continue;
                    }
                    ARCHIVO_POL = PoliticaDeAcceso.getRUTA_POLITICAS() + datos[3].trim() + ".txt";
                    flag = true;
                } else if (usuario.trim().equalsIgnoreCase("salir")) {
                    return;
                } else {
                    System.out.println("Error: Usuario no encontrado. Por favor intente de nuevo");
                    flag = false;
                }
            } catch (Exception err){
                System.out.println("Error fatal, no se cargaron los usuarios correctamente");
                return;
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
                    } else {
                        System.out.println("Columnas guardadas con éxito");
                        flag = true;
                    }
                }
                
                case "salir" -> {return;}
                
                default -> nuevas_columnas.add(columna);
                
            }
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
            Usuario.crearLog("Creación de regla", this.ID_Usuario, "Exitoso");
        } catch (IOException err){
            err.printStackTrace();
            System.out.println("Error de I/O: No se pudo escribir en el archivo");
            Usuario.crearLog("Creación de regla", this.ID_Usuario, "Fallido");
        }
        
    }
    
    private void crearUsuario(){
        boolean flag = false;
        String nuevo_ID = "";
        String nombre_nuevo = "";
        String tipo = "";
        String departamento_nuevo;
        String linea_nueva = "";
        String nuevo_ID_pol = "POL-";
        String nueva_contrasena;
        String secuencia;
        
        try{
            int siguiente_usu = InicioSesion.auxiliar_nombre_ID.size() + 1;
            secuencia = String.format("%0" + 4 + "d", siguiente_usu);
            
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
        } catch (Exception err){
            System.out.println("Error fatal, los usuarios no se cargaron correctamente");
            return;
        }

        flag = false;
        while (!flag){
            System.out.println("Ingrese el tipo de usuario (Interno, externo, o 'fin' para cancelar): ");
            tipo = entry.nextLine().trim().toLowerCase();
            
            switch(tipo.trim().toLowerCase()){
                
                case "fin" -> {return;}
                    
                case "interno" -> {
                    flag = true;
                    nuevo_ID = "UIN-" + secuencia.trim();
                    //nuevo_ID = nuevo_ID + secuencia;
                    System.out.println("Ingrese el departamento al que pertenece el nuevo usuario (o 'fin' para cancelar): ");
                    departamento_nuevo = entry.nextLine().trim();
                    if (departamento_nuevo.trim().equalsIgnoreCase("fin")){return;}
                    linea_nueva = nuevo_ID.trim() + "," + nombre_nuevo.trim() + "," + tipo.trim() + "," + departamento_nuevo.trim();
                }
                    
                case "externo" -> {
                    flag = true;
                    nuevo_ID = "UEX-" + secuencia;
                    //nuevo_ID = nuevo_ID + secuencia;
                    nuevo_ID_pol = PoliticaDeAcceso.generarIDPolitica();
                    linea_nueva = nuevo_ID.trim() + "," + nombre_nuevo.trim() + "," + tipo.trim() + "," + nuevo_ID_pol.trim();
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
            Usuario.crearLog("Creación de usuario", this.ID_Usuario, "Exitoso");
        } catch (IOException err){
            err.printStackTrace();
            System.out.println("Error de I/O: No se pudo escribir en el archivo");
            Usuario.crearLog("Creación de usuario", this.ID_Usuario, "Fallido");
        }
        
        String ARCHIVO_CREDENCIALES = InicioSesion.getARCHIVO_CREDENCIALES();
        try(BufferedWriter guardar_credenciales = new BufferedWriter(new FileWriter(ARCHIVO_CREDENCIALES, true))){
            guardar_credenciales.write(nuevo_ID.trim() + "," + nueva_contrasena.trim());
            guardar_credenciales.newLine();
            System.out.println("Credenciales de acceso creadas exitosamente");
        } catch (IOException err){
            err.printStackTrace();
        }
            
        if (tipo.equalsIgnoreCase("externo")){
            //falta poner la ubicacion de archivo
            String ruta = PoliticaDeAcceso.getRUTA_POLITICAS() + nuevo_ID_pol.trim() + ".txt";
            File nueva_pol = new File(ruta);
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
        try{
            InicioSesion.cargarDatosUsuario();
            while (true){
                System.out.println("Ingrese el nombre del usuario a consultar datos (o 'fin' para cancelar): ");
                String usu = entry.nextLine().trim();

                if (InicioSesion.auxiliar_nombre_ID.get(usu.toLowerCase()) != null){
                    String[] datos = InicioSesion.getLista_datos_usu(usu);
                    switch (datos[2].trim()){
                        case "interno" -> System.out.println("Datos del usuario ingresado:\n" + "|Id: " + datos[0] + "\n" + "|Nombre: " + datos[1] + "\n" + "|Tipo de usuario: " + datos[2] + "\n" + "|Departamento del usuario: " + datos[3] + "\n");
                        case "externo" -> System.out.println("Datos del usuario ingresado:\n" + "|Id: " + datos[0] + "\n" + "|Nombre: " + datos[1] + "\n" + "|Tipo de usuario: " + datos[2] + "\n" + "|ID de política del usuario: " + datos[3] + "\n");
                    }
                    Usuario.crearLog("Vista de info de usuario", this.ID_Usuario, "Exitoso");
                } else if (usu.trim().equalsIgnoreCase("fin")) {
                    return;
                } else {
                    System.out.println("Error: El usuario no existe.");
                }
                
            }
        } catch (Exception err){
            System.out.println("Error fatal, los usuarios no se cargaron correctamente");
            Usuario.crearLog("Vista de info de usuario", this.ID_Usuario, "Fallido");
        }
    }
    
    private void verPolitica(){
        try{
            InicioSesion.cargarDatosUsuario();
            while (true){
                System.out.println("Ingrese el nombre del usuario a consultar política (o 'fin' para cancelar): ");
                String usu = entry.nextLine().trim();

                if (InicioSesion.auxiliar_nombre_ID.get(usu.toLowerCase()) != null){
                    String[] datos = InicioSesion.getLista_datos_usu(usu);
                    switch (datos[2].trim()){
                        case "externo" -> {
                            PoliticaDeAcceso politica = new PoliticaDeAcceso(datos[3].trim());

                            politica.cargarPolitica();
                            ArrayList<Regla> lista_reglas = politica.getPolitica();
                            
                            System.out.println("ID de política del usuario ingresado: " + datos[3]);
                            System.out.println("\n--- Reglas de acceso disponibles: ---");

                            for (int i =  0; i < lista_reglas.size(); i++){
                                Regla regla = lista_reglas.get(i);
                                String columnas = String.join(", ", regla.columnas_permitidas);
                                System.out.println("---------\n" + "Regla #" + i + ":\n" + "|Fecha máxima autorizada: " + regla.fecha_vencimiento.toString() + "\n" + "|Conjunto de datos: " + regla.conjunto_datos + "\n" + "|Tabla: " + regla.tabla_permitida + "\n" + "|Columnas permitidas: " + columnas + "\n");
                            }
                            Usuario.crearLog("Vista de política de usuario", this.ID_Usuario, "Exitoso");
                        }
                        case "interno" -> {
                            System.out.println("Error: Los usuarios internos no tienen política de acceso. Por favor vuelva a intentar");
                        }
                    }
                } else if (usu.trim().equalsIgnoreCase("fin")) {
                    return;
                } else {
                    System.out.println("Error: El usuario no existe.");
                }
                
            }
            
        } catch (Exception err){
            
            Usuario.crearLog("Vista de política de usuario", this.ID_Usuario, "Fallido");
        }
    }
    
    @Override
    public void verInfo(){
        System.out.println("Datos del Usuario:\n" + "|Id: " + this.ID_Usuario + "\n" + "|Nombre: " + this.nombre + "\n" + "|Tipo de usuario: " + this.tipo_de_usuario + "\n" + "|Departamento del usuario: " + this.departamento + "\n");
    }
    
    @Override
    public void verMenu(){
        
        while (true){
            try{
                System.out.println();
                System.out.println("Bienvenido, " + this.nombre + ". Por favor elija una opción: ");
                System.out.println("Opción 1: Ver logs de acceso");
                System.out.println("Opción 2: Crear usuario");
                System.out.println("Opción 3: Crear regla para un usuario");
                System.out.println("Opción 4: Ver info de un usuario seleccionado");
                System.out.println("Opción 5: Ver info del usuario logueado");
                System.out.println("Opción 6: Ver política de usuario seleccionado");
                System.out.println("Opción 7: Cerrar sesión");
                int opcion = entry.nextInt();
                entry.nextLine();

                switch (opcion) {
                    case 1 -> verLogs();
                    case 2 -> this.crearUsuario();
                    case 3 -> this.crearRegla();
                    case 4 -> this.verInfoUsu();
                    case 5 -> this.verInfo();
                    case 6 -> this.verPolitica();
                    case 7 -> {return;}
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
