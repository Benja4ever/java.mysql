package com.mycompany.jugueteria;

/**
 *
 * @author Brian Guerra C.
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jugueteria {

    static Scanner entrada = new Scanner(System.in);
    public static ArrayList<Juego> juguetes = new ArrayList<>();

    public static Connection conexionBD = null;

    public static void main(String[] args) {

        try {
            // Establecemos la conexion a la bd
            conexionBD = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");// Conecta con el root
            // creamos un statement para realizar la query
            Statement sentencia = conexionBD.createStatement();
            sentencia.executeUpdate("CREATE DATABASE IF NOT EXISTS JUGUETERIA");
            sentencia.executeUpdate("USE JUGUETERIA");
            // creamos una tabla nueva producto con cinco campos
            sentencia.executeUpdate("CREATE TABLE IF NOT EXISTS juego_mesa (id INT, PRIMARY KEY(id),  nombre VARCHAR(20),"
                    + "precio DOUBLE, tipo VARCHAR(20), marca VARCHAR(20))");
            sentencia.executeUpdate("CREATE TABLE IF NOT EXISTS juego_CONSTRUCCION (id INT, PRIMARY KEY(id), nombre VARCHAR(20),"
                    + "precio DOUBLE, tipo_bloque VARCHAR(20))");

        } catch (Exception e) {
            e.printStackTrace();
        }

        int opcion;

        do {
            mostrarMenu();
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    alta();
                    break;
                case 2:
                    consultar();
                    break;
                case 3:
                    modificar();
                    break;
                case 4:
                    salir();
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 6);
    }
//
//    
//

    private static void mostrarMenu() {
        System.out.println("--- Juguetería Online ---");
        System.out.println("1. Alta del jueguete");
        System.out.println("2. Consulta del juguete");
        System.out.println("3. Modificación precio");
        System.out.println("4. Salir del programa");
    }

    private static void alta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Introduce el código del producto:");
        int codigo = entrada.nextInt();

        System.out.println("Introduce el nombre del producto:");
        String nombre = entrada.next();

        System.out.println("Introduce el precio del producto:");
        double precio = entrada.nextDouble();

        System.out.println("El juguete es: \n1. Juego de mesa + \n2. Juego de construcción");

        //Si el juego es de mesa te pedirá dos valores adicionales, si es de construcción solo 
        //un valor adicional. si no está entre las dos opciones nos devolverá un mensaje de error
        int juego = entrada.nextInt(); //la variable juego es de tipo int(opciones 1 ó 2)
        if (juego == 1) { //si la opción seleccionada es la 1 solicitará los siguientes datos:
            System.out.println("Introduce la marca del producto:");
            String marca = entrada.next();
            System.out.println("Introduce el tipo del producto:");
            String tipo = entrada.next();
            JuegoMesa juguete = new JuegoMesa(marca, tipo, codigo, nombre, precio);
            System.out.println("Jueguete creado " + juguete);
            juguetes.add(juguete);
            Statement sentencia;
            try {
                sentencia = conexionBD.createStatement();
                String insertQuery = "INSERT INTO juego_mesa (id, nombre, precio, tipo, marca) VALUES('" + codigo + "', '" + nombre + "', '" + precio + "', '" + tipo + "', '" + marca + "')";
                sentencia.executeUpdate(insertQuery);
            } catch (SQLException ex) {
                System.out.println("Error al insertar juego_mesa");
                ex.printStackTrace();
            }

        } else if (juego == 2) { //si la opción seleccionada es la 2 solicitará los siguientes datos:
            System.out.println("Introduce el tipo de bloque del producto:");
            String tipoBloque = entrada.next();
            JuegoConstruccion juguete = new JuegoConstruccion(tipoBloque, codigo, nombre, precio);
            System.out.println("Jueguete creado " + juguete);
            juguetes.add(juguete);
            Statement sentencia;
            try {
                sentencia = conexionBD.createStatement();
                String insertQuery = "INSERT INTO juego_CONSTRUCCION (id, nombre, precio, tipo_bloque) VALUES('" + codigo + "', '" + nombre + "', '" + precio + "', '" + tipoBloque + "')";
                sentencia.executeUpdate(insertQuery);
            } catch (SQLException ex) { //si se genera una ecepción se mostrará el mensaje de error y no guardará los datos solicitados.
                System.out.println("Error al insertar juego_CONSTRUCCION");
                ex.printStackTrace();
            }
//        } else {
//            System.out.println("Juego no encontrado.");
        }
    }

    private static void consultar() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("¿Qué tipo de juego deseas ver? \n1. Juego de mesa \n2. Juego de construcción");
        int opcion = entrada.nextInt();

        for (Juego j : juguetes) { //listado de todos los juegos del arraylist
            if (opcion == 1) {
                if (j instanceof JuegoMesa) {//instanceof me dice si el elemento pertenece a esa clase
                    System.out.println(j); // imprime y va al siguiente   
                }
            } else if (opcion == 2) {
                if (j instanceof JuegoConstruccion) {
                    System.out.println(j); // imprime y va al siguiente
                }
            }
        }
        Statement sentencia = null; // 
        ResultSet rs = null; // 

        try {
            sentencia = conexionBD.createStatement();
            if (opcion == 1) { // si la opción seleccionada es l hará un select total a la tabla juego_mesa
                System.out.println("Los datos almacenados en la tabla 'juego_mesa' son:");
                rs = sentencia.executeQuery("SELECT * FROM juego_mesa");
            } else if (opcion == 2) { // si la opción seleccionada es 2 hará un select total a la tabla juego_CONSTRUCCION
                System.out.println("Los datos almacenados en la tabla 'juego_CONSTRUCCION' son:");
                rs = sentencia.executeQuery("SELECT * FROM juego_CONSTRUCCION");
            }

            while (rs.next()) { // irá recorriendo el los ResultSet ak siguiente
                if (opcion == 1) { //mostrará todos los datos almacenados en la opción 1 juego_mesa
                    System.out.println("ID: " + rs.getInt("id") //orden de como se mostrará la consuklta
                            + " Nombre: " + rs.getString("nombre")
                            + " Precio: " + rs.getDouble("precio")
                            + " Marca: " + rs.getString("marca")
                            + " Tipo: " + rs.getString("tipo"));
                } else if (opcion == 2) {//mostrará todos los datos almacenados en la opción 2 juego_CONSTRUCCION'
                    System.out.println("ID: " + rs.getInt("id")
                            + " Nombre: " + rs.getString("nombre")
                            + " Precio: " + rs.getDouble("precio")
                            + " Tipo de Bloque: " + rs.getString("tipo_bloque"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void modificar() {
        double nuevoPrecio = 0;
        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduce el nombre del producto que desees modificar: ");
        String nombreBuscado = entrada.nextLine();
        boolean encontrado = false;

        //Actualizamos el precio del juego
        for (Juego j : juguetes) {//listado de todos los juegos del arraylist
            if (j.getNombre().equalsIgnoreCase(nombreBuscado)) {
                System.out.println("Introduce el nuevo precio del producto:");
                //función para reestablecer el precio del producto en la posición i
                nuevoPrecio = entrada.nextDouble();
                j.setPrecio(nuevoPrecio);
                System.out.println("Producto modificado con éxito en el ArrayList.");
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("El juguete no ha sido encontrado. No se puede modificar.");
            return;
        }
        for(Juego j : juguetes)
            System.out.println(j);

        System.out.println("Producto modificado con éxito.");
        //devolver el usuario al menú

// Actualizamos el precio en la base de datos
        Statement sentencia = null;

        try { // siempre deben ir alrededor de todo lo que va en las bases de datos, se deben controlar las ecepciones
            sentencia = conexionBD.createStatement();

            // Determinamos si el producto es un juego de mesa o un juego de construcción
            String updateQueryMesa = "UPDATE juego_mesa SET precio = " + nuevoPrecio + " WHERE nombre = '" + nombreBuscado + "'";
            String updateQueryConstruccion = "UPDATE juego_CONSTRUCCION SET precio = " + nuevoPrecio + " WHERE nombre = '" + nombreBuscado + "'";

            int filasAfectadas = sentencia.executeUpdate(updateQueryMesa); //Si la fila afectada me devuelve cero, pasa a la siguiente fila.

            if (filasAfectadas == 0) {
                filasAfectadas = sentencia.executeUpdate(updateQueryConstruccion);
            }

            if (filasAfectadas> 0) {
                System.out.println("Producto modificado con éxito en la base de datos.");
            } else {
                System.out.println("Producto no encontrado en la base de datos. No se puede modificar.");
            }
        } catch (SQLException ex) { //busca la ecepcion de las sentencias de los try
            ex.printStackTrace(); // te muestra los errores que estan pasando (lineas azules)
        } finally {// siempre se ejecuta, aqui tiene un try y un catch para cerrar la sentencia si es distinta a null
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    private static void salir() {
        System.out.println("Muchas gracias por visitarnos. Hasta pronto!");
        System.exit(0);

    }
}
        
