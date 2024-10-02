package ejercicio;

import java.util.Scanner;

public class Menu {
    static Scanner sc = new Scanner(System.in);
    Gestor g = new Gestor();
    private static int opcion;
    private static int opcionFichero;

    public Menu(){
        menu();
    }

    public void menu(){
        System.out.println("----------------------------------");
        System.out.println("1. Añadir un videojuego");
        System.out.println("2. Eliminar un videojuego");
        System.out.println("3. Mostrar todos los videojuegos");
        System.out.println("4. Guardar datos a un fichero");
        System.out.println("5. Recuperar datos de un fichero");
        System.out.println("0. Salir");
        System.out.println("----------------------------------");

        if(sc.hasNextInt()){
            opcion = sc.nextInt();
            sc.nextLine();
        } else {
            System.err.println("Error: No ha introducido un número válido.");
            sc.next();
            menu();
        }

        ejecutar();
    }

    private void menuFicheros(){
        System.out.println("----------------------------------");
        System.out.println("1. Fichero de texto");
        System.out.println("2. Fichero binario");
        System.out.println("3. Fichero XML");
        System.out.println("0. Atrás");
        System.out.println("----------------------------------");

        if(sc.hasNextInt()){
            opcionFichero = sc.nextInt();
            sc.nextLine();
        } else {
            System.err.println("Error: No ha introducido un número válido.");
            sc.next();
            menuFicheros();
        }

        if(opcionFichero == 0){
            menu();
        }
    }

    private void ejecutar(){
        switch(opcion){
            case 1:
                g.addVideojuego();
                break;
            case 2:
                g.elimVideojuego();
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
        if(opcion != 0){
            menu();
        }
    }

        
}
