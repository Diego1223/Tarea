//Tenemos un sistema de gestion de CARNICERIA
//Lo primero que tenemos que tomar en cuenta es que tenemos 3 tablas de 3 tipos de datos
//Res, pollo y pescado, cada tabla tiene los tipos id, nombre, stock, precio_kg

import com.mysql.cj.protocol.Message;
import java.sql.*;
import java.util.Scanner;

//No se puede instanciar objetos de esta clase
abstract class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/tarea_carnes?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "tuclave";

    private Connection conexion;

    //Constructor
    public Conexion() {
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexion");
        }
    }
    //Este metodo mostrara los productos, junto con su id, nombre, stock y precio_kg
    //Para no escribir tanto codigo, recibe un string tipo para seleccionar la tabla
    //Tenemos 3 tablas
    public void mostrar_datos(String tipo) {
        if (conexion != null) {
            String query = "SELECT * FROM " + tipo;
            try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

                //Recorrer los resultados para mostrarlos de una manera adecuada
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    int stock = rs.getInt("stock");
                    int precio_kg = rs.getInt("precio_kg");

                    System.out.println(id + " | " + nombre + " | " + stock + " | " + precio_kg);
                }
            } catch (SQLException e) {
                System.out.println("Error al mostrar datos" + e.getMessage());
            }
        } else {
            System.out.println("No se pudo establecer la conexion");
        }
    }
}

//Esta es una clase hija de conexion
class Res extends Conexion {
    //Constructor
    public Res() {
        System.out.println("Esto es variedad de carne de res");
        mostrar_datos("Res");
    }
}

class Pollo extends Conexion {
    public Pollo() {
        System.out.println("Esto es variedad de carne de pollo");
        mostrar_datos("Pollo");
    }
}

class Pescado extends Conexion {
    public Pescado() {
        System.out.println("Esto es variedad de carne de pescado");
        mostrar_datos("Pescado");
    }
}

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("Bienvenido al sistema Gestion de base datos carniceria\nCual deseas modificar");
        String [] opciones = {"Res", "Pollo", "Pescado"};

        //Este i lo usamos para poner indice numerico
        int i = 1;
        for (String str: opciones) {
            System.out.println(i + ". " + str);
            i++;
        }

        //Esto lo hacemos para poder ingresar datos por teclado
        Scanner scanner = new Scanner(System.in);
        System.out.print("=> ");
        int op = scanner.nextInt();

        switch (op) {
            case 1:
                Res rs = new Res();
                break;
            case 2:
                Pollo pl = new Pollo();
                break;
            case 3:
                Pescado ps = new Pescado();
                break;
        }
    }
}

