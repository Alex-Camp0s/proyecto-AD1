package ejercicio;

import java.util.Scanner;

public class Menu {
    private final static Scanner sc = new Scanner(System.in);
    private final Gestor g = new Gestor();
    private static int opcion;
    private static int opcionFichero;
    private static boolean errorLectura = false;

    public Menu() {
        menu();
    }

    public void menu() {
        System.out.println("----------------------------------");
        System.out.println("1. Añadir un videojuego");
        System.out.println("2. Eliminar un videojuego");
        System.out.println("3. Mostrar todos los videojuegos");
        System.out.println("4. Guardar datos a un fichero");
        System.out.println("5. Recuperar datos de un fichero");
        System.out.println("0. Salir");
        System.out.println("----------------------------------");

        opcion = leerInt();
        if(errorLectura) {
            menu();
            errorLectura = false;
        }

        ejecutar();
    }

    private void menuFicheros() {
        System.out.println("----------------------------------");
        System.out.println("1. Fichero de texto");
        System.out.println("2. Fichero binario");
        System.out.println("3. Fichero XML");
        System.out.println("0. Atrás");
        System.out.println("----------------------------------");

        opcionFichero = leerInt();
        if(errorLectura) {
            menuFicheros();
            errorLectura = false;
        }

        if(opcionFichero == 0) {
            menu();
        }
    }

    private void ejecutar() {
        switch(opcion) {
            case 1:
                g.addVideojuego(pedirDatos());
                break;
            case 2:
                g.elimVideojuego(pedirDatos());
                break;
            case 3:
                g.listarVideojuegos();
                break;
            case 4:
                menuFicheros();
                g.guardar(opcionFichero);
                break;
            case 5:
                menuFicheros();
                g.cargar(opcionFichero);
                break;
            case 0:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Ha introducido una opción inválida, intentelo de nuevo");
                menu();
        }
        if(opcion != 0) {
            menu();
        }
    }

    public Videojuego pedirDatos() {
        Videojuego v = null;
        String nombre;
        System.out.println("Introduzca el nombre del videojuego:");
        nombre = sc.nextLine();
        if(opcion == 1) {
            System.out.println("Introduzca el año de publicación");
            int anoPublicacion = leerInt();
            System.out.println("Introduzca la nota que le da al videojuego");
            double nota = leerDouble();
            System.out.println("Está disponible para consolas?\n1.Sí\n2.No");
            boolean consola = leerInt() == 1;
            if(!errorLectura) {
                try {
                    v = new Videojuego(nombre, anoPublicacion, nota, consola);
                } catch(IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Error: No se ha podido añadir el videojuego a la base de datos");
                errorLectura = false;
            }
        } else {
            v = g.getVideojuego(nombre);
        }
        return v;
    }

    private int leerInt() {
        int leido = 0;
        if(sc.hasNextInt()) {
            leido = sc.nextInt();
            sc.nextLine();
        } else {
            sc.next();
            System.err.println("Error: No ha introducido un número válido");
            errorLectura = true;
        }
        return leido;
    }

    private double leerDouble() {
        double leido = 0;
        if(sc.hasNextDouble()) {
            leido = sc.nextDouble();
            sc.nextLine();
        } else {
            sc.next();
            System.err.println("Error: No ha introducido un número válido");
            errorLectura = true;
        }
        return leido;
    }

}
