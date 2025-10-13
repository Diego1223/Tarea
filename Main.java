import java.sql.*;
import java.util.Scanner;

class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/pruebas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "tuclave";

    private Connection conexion;

    //Constructor
    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error de conexion");
            return null;
        }
    }


    public static void crearUsuario(Scanner sc) {
        System.out.print("Nombre -> ");
        String nombre = sc.nextLine();
        System.out.print("email -> ");
        String email = sc.nextLine();

        String sql = "INSERT INTO usuarios (nombre,email) VALUES (?,?)";
        try (Connection conn = conectar(); PreparedStatement stmt =conn.prepareStatement(sql)){
            stmt.setString(1, nombre);
            stmt.setString(2, email);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario creado correctamente");
            } else {
                System.out.println("No se inserto ningun registro!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Lista de usuarios ---");
            while (rs.next()) {
                System.out.printf("%d | %s | %s%n",
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void actualizarUsuario(Scanner sc) {
        System.out.print("ID del usuario a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Nuevo email: ");
        String email = sc.nextLine();

        String sql = "UPDATE usuarios SET nombre=?, email=? WHERE id=?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setInt(3, id);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario actualizado");
            } else {
                System.out.println("No se encontró ese ID");
            }
        } catch (SQLException e) {
            System.out.println("Error desconocido");
        }
    }

    public static void eliminarUsuario(Scanner sc) {
        System.out.print("ID del usuario a eliminar: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM usuarios WHERE id=?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println(" Usuario eliminado");
            } else {
                System.out.println("No se encontró el ID");
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
}



public class Main {
    public static String centrar_texto(String cadena) {
        int ancho_terminal = 150;
        int longuitud_texto = cadena.length();
        int espacios_totales = ancho_terminal - longuitud_texto;
        int espacios_izq = espacios_totales / 2;
        int espacios_der = espacios_totales - espacios_izq;

        String padding_izq = " ".repeat(espacios_izq);
        String padding_der = " ".repeat(espacios_der);

        String resultado = (padding_izq + cadena + padding_der);
        return resultado;

    }


    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println(centrar_texto("Sistema CRUD"));

        while (true) {
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");
            System.out.print("=> ");
            int opcion = sc.nextInt();
            sc.nextLine(); //Limpiar el buffer

            switch (opcion) {
                case 1 -> Conexion.crearUsuario(sc);
                case 2 -> Conexion.listarUsuarios();
                case 3 -> Conexion.actualizarUsuario(sc);
                case 4 -> Conexion.eliminarUsuario(sc);
                case 5 -> {
                    System.out.println("SALIENDO...");
                    return;
                }
                default -> System.out.println("Opcion invalida");
            }
        }
    }

}
