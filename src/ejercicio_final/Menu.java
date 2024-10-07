package ejercicio_final;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase Menú que muestra un menú con opciones por pantalla.<br>
 * En dicho menú el usuario debe introducir cuál de las opciones desea ejecutar, lee cuál es la opción que el usuario ha
 * elegido y llama a los métodos correspondientes para ejecutar la opción que el usuario haya elegido.<br>
 * <p>
 * Para leer los datos que el usuario introduzca tiene dos métodos, uno para leer enteros y otro para leer números
 * decimales. En cada uno de ambos se comprueba si el dato que se va a leer es correcto y sino devuelve un error.
 *
 * @author Alejandro Campos
 * @version 04102024A
 */
public class Menu {
    private final static Scanner sc = new Scanner(System.in);
    private final Gestor g = new Gestor();
    private static int opcion;
    private static int opcionFichero;
    private static boolean errorLectura = false;
    private static boolean salir = false;
    private boolean sobreescribirDatos = false;

    /**
     * Constructor de un objeto menú que al instanciar el objeto llama al metodo menú para mostrarlo directamente.
     */
    public Menu() {
        menu();
    }

    /**
     * Metodo que muestra un menú por pantalla donde muestra las diferentes opciones que puede elegir el usuario.
     * <p>
     * Lee por pantalla la opción que quiera el usuario y si no ha habido ningún error durante la lectura se llama al
     * metodo para ejecutar la opción que ha elegido.
     * <p>
     * En el caso de que haya habido algún error durante la lectura de la opción se vuelve a invocar el menú para que
     * lo vuelva a intentar con una opción correcta.
     * <p>
     * Se ejecutará el menú hasta que el usuario no elija la opción de salida del programa la cual será la última
     * ejecución del programa.
     */
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
            errorLectura = false;
            menu();
        }

        if(!salir) {
            ejecutar();
        }
    }

    /**
     * Metodo que muestra un menú por pantalla donde muestra las diferentes opciones de ficheros que puede elegir el
     * usuario cuando elije alguna de las opciones de guardar o recuperar datos de un fichero.
     * <p>
     * Este metodo es invocado una vez el usuario ha elegido alguna de las opciones relacionadas con los ficheros
     * donde se guardan los datos del programa.
     * <p>
     * En el caso de que haya habido algún error durante la lectura de la opción se vuelve a invocar el menú para que
     * lo vuelva a intentar con una opción correcta.
     * <p>
     * Cuando el usuario elija la opción de salida del menú, volverá al menú principal donde puede volver a elegir
     * alguna de las otras opciones
     */
    private void menuFicheros() {
        System.out.println("----------------------------------");
        System.out.println("1. Fichero de texto");
        System.out.println("2. Fichero binario");
        System.out.println("3. Fichero XML");
        System.out.println("0. Atrás");
        System.out.println("----------------------------------");

        opcionFichero = leerInt();
        if(errorLectura) {
            errorLectura = false;
            menuFicheros();
        }

        if(opcionFichero == 0) {
            menu();
        }
    }

    /**
     * Metodo que ejecuta la opción que haya elegido el usuario. En el caso de las opciones relacionadas con ficheros
     * llama al menú para que elija que tipo de fichero desea utilizar el usuario para posteriormente ejecutar la
     * opción deseada.
     * <p>
     * Cuando se elije la opción de salida del programa cambia la variable global salir a verdadero para que el
     * programa sepa que no se debe ejecutar nada más.
     * <p>
     * En caso de que se haya introducido una opción invalida se le indica al usuario que la opción escogida no es
     * válida y se vuelve a mostrar el menú para que elija una opción correcta.
     * <p>
     * Una vez haya sido ejecutada la opción escogida por el usuario y si no ha sido la de salida del programa se
     * vuelve a llamar al menú para que siga la ejecución del programa.
     * <p>
     * Si el usuario ha elegido una de las opciones relacionadas con ficheros lo primero que hace es llamar al menú
     * de opciones de ficheros que tiene el programa para que el usuario elija cuál de todos los ficheros quiere
     * utililzar. Una vez ya haya escogido el fichero correspondiente, se comprueba que la opcion elegida se pueda
     * ejecutar en el programa, sino se muestra un error. Si es una opción válida se le pide el nombre del archivo y
     * se comprueba que tenga una extensión correcta según la opción elegida. Una vez hecho eso, se comprueba si el
     * archivo existe, si no existe se le da la opción de crearlo. Si se crea o existe el fichero se llama al metodo
     * para ejecutar la opción escogida, sino se muestra un mensaje de error.
     */
    private void ejecutar() {
        switch(opcion) {
            case 1:
                g.addVideojuego(pedirDatosVideojuego());
                break;
            case 2:
                g.elimVideojuego(pedirDatosVideojuego());
                break;
            case 3:
                g.listarVideojuegos();
                break;
            case 4:
                menuFicheros();
                if(opcionFichero >= 1 && opcionFichero <= 3) {
                    System.out.println("Introduzca el nombre del archivo donde guardar los datos, no es necesario " +
                                       "incluir la extensión de archivo");
                    File archivo = new File(sc.nextLine());
                    archivo = extensionArchivo(archivo);
                    if(comprobarArchivo(archivo)) {
                        if(sobreescribirDatos) {
                            g.guardar(opcionFichero, archivo);
                        } else {
                            System.out.println("No se han guardado los datos");
                        }
                    } else {
                        System.err.println("Error: El archivo indicado no existe, no se han guardado los datos");
                    }
                } else if(opcionFichero != 0) {
                    System.err.println("Error: No existe el tipo de fichero elegido");
                }
                break;
            case 5:
                menuFicheros();
                if(opcionFichero >= 1 && opcionFichero <= 3) {
                    System.out.println("Introduzca el nombre del archivo desde donde cargar los datos, no es " +
                                       "necesario incluir la extensión de archivo");
                    File archivo = new File(sc.nextLine());
                    archivo = extensionArchivo(archivo);
                    if(comprobarArchivo(archivo)) {
                        if(sobreescribirDatos) {
                            g.cargar(opcionFichero, archivo);
                        } else {
                            System.out.println("No se han cargado los datos");
                        }
                    } else {
                        System.out.println("El archivo indicado no existe, no se han cargado los datos");
                    }
                } else if(opcionFichero != 0) {
                    System.out.println("No existe el tipo de fichero elegido");
                }
                break;
            case 0:
                salir = true;
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Ha introducido una opción inválida, intentelo de nuevo");
                menu();
        }
        if(!salir) {
            menu();
        }
    }

    /**
     * Metodo que es invocado cuando se quiere añadir o eliminar un videojuego de la base de datos del programa.
     * <p>
     * Primero se pide el nombre y luego según la opción que haya elegido el usuario se pide el resto de datos o se
     * busca en la base de datos a ver si el videojuego que ha introducido el usuario se encuentra en la base de datos.
     * <p>
     * En caso de que se haya elegido añadir un videojuego a la base de datos se piden el año en el que fue
     * publicado, la nota que el usuario le da al videojuego y si está disponible para consolas.
     * Se leen los datos y si no ha habido ningún error durante la lectura de datos, se intenta crear el objeto. Si
     * todos los datos introducidos son válidos se crea el objeto y si el videojuego no se encontraba en la base de
     * datos se introduce a ella. En cambio si durante la creación del objeto alguno de los datos no es válido, el
     * objeto no se crea y se le muestra por pantalla al usuario cuál ha sido el error durante la creación del objeto.
     * En caso de que durante la lectura de datos se detecte algún error, el objeto no se intentará crear y le pedira
     * al usuario que vuelva a intentarlo y le volverá a mostrar el menú para que elija alguna opción.
     * <p>
     * Si el usuario ha escogido la opción de eliminar un videojuego de la base de datos del programa solo se le
     * pedirá el nombre del videojuego que quiere eliminar y el programa se pondrá a buscar en la base de datos. Si
     * lo encuentra se eliminará de la base de datos. Si no lo encuentra devolverá nulo.
     *
     * @return videojuego con los datos introducidos por el usuario si se crea correctamente, videojuego con los
     * datos recogidos de la base de datos o nulo si algún dato introducido ha sido incorrecto o no se ha encontrado
     * el videojuego en la base de datos
     */
    public Videojuego pedirDatosVideojuego() {
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
                System.err.println("Uno o más datos no son válidos. Inténtelo de nuevo.");
                errorLectura = false;
            }
        } else {
            Videojuego v2 = new Videojuego(nombre);
            v = g.getVideojuego(v2);
        }
        return v;
    }

    /**
     * Metodo utilizado para la lectura de datos numéricos de tipo entero.
     * Para efectuar la lectura de los datos primero se comprueba que el usuario haya introducido un número entero.
     * En caso de que sí que se haya introducido un entero, se lee y se guarda en una variable que se devolverá.
     * <p>
     * Si no ha sido introducido un numero entero devolverá un error y se devolverá un -1
     *
     * @return número leído por pantalla si no ha sucedido ningún error durante la lectura, -1 si ha ocurrido algún
     * error durante la lectura
     */
    private int leerInt() {
        int leido = -1;
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

    /**
     * Metodo utilizado para la lectura de datos numéricos de tipo flotante.
     * Para efectuar la lectura de los datos primero se comprueba que el usuario haya introducido un número flotante.
     * En caso de que sí que se haya introducido un flotante, se lee y se guarda en una variable que se devolverá.
     * <p>
     * Si no ha sido introducido un numero flotante devolverá un error y se devolverá un -1
     *
     * @return número leído por pantalla si no ha sucedido ningún error durante la lectura, -1 si ha ocurrido algún
     * error durante la lectura
     */
    private double leerDouble() {
        double leido = -1;
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

    /**
     * Metodo que comprueba si el archivo desde el que se van a cargar los datos existe o no.
     * <p>
     * Si el archivo existe y se desean guardar datos se le pregunta al usuario si se quieren sobreescribir los datos
     * que hubiera en el fichero previamente. Lo que haya elegido se guarda en una variable que se enviará junto al
     * archivo y la opción deseada para guardar los datos.
     * <p>
     * Si el archivo no existiera se le pregunta al usuario si quiere crear el archivo. En caso de que elija que no
     * quiere crear el archivo se sale del metodo y no se ejecuta la opción que el usuario haya elegido
     *
     * @param archivo archivo sobre el que se quieren realizar operaciones
     *
     * @return <code>true</code> si el archivo existe, <code>false</code> si el archivo no existe
     */
    private boolean comprobarArchivo(File archivo) {
        if(!archivo.exists()) {
            System.out.println("El archivo " + archivo.getName() + " no existe. ¿Desea crearlo?\n1. Sí\n2. No");
            int opc = leerInt();
            if(opc == 1) {
                try {
                    archivo.createNewFile();
                    System.out.println("El archivo ha sido creado correctamente");
                    sobreescribirDatos = true;
                } catch(IOException e) {
                    System.err.println("Ha ocurrido algún error durante la creación del archivo");
                }
            } else {
                System.out.println("No se ha creado el archivo");
            }
        } else if(archivo.exists() && opcion == 4) {
            System.out.println("Se sobreescribirá el archivo. ¿Está seguro?\n1. Sí\n2. No");
            int opc = leerInt();
            sobreescribirDatos = opc == 1;
        } else if(archivo.exists() && opcion == 5) {
            System.out.println("Se sobreescribirán los datos guardados en el programa. ¿Está seguro?\n1. Sí\n2. No");
            int opc = leerInt();
            sobreescribirDatos = opc == 1;
        }
        return archivo.exists();
    }

    /**
     * Metodo que comprueba si un archivo introducido lleva la extensión de archivo correspondiente a la opción que
     * haya elegido el usuario.
     * <p>
     * Si el archivo lleva incluída la extensión de archivo correspondiente se devuelve el mismo archivo.
     * <p>
     * Si el archivo no lleva la extensión de archivo correspondiente se devuelve el archivo con la extensión del
     * archivo correspondiente a la opcrión que haya elegido el usuario.
     * <p>
     * Correspondencias de extensiones de archivos a opciones del usuario:
     * - Fichero de texto: .txt
     * - Fichero binario: .dat
     * - Fichero xml: .xml
     *
     * @param archivo archivo a comprobar si contiene la extensión de archivo correspondiente.
     *
     * @return archivo con la extensión de archivo correspondiente a la opción que el usuario haya elegido.
     */
    private File extensionArchivo(File archivo) {
        if((archivo.getName().endsWith(".txt") && opcionFichero == 1) ||
           (archivo.getName().endsWith(".dat") && opcionFichero == 2) ||
           (archivo.getName().endsWith(".xml")) && opcionFichero == 3) {
            return archivo;
        } else {
            String nombre = archivo.getName();
            nombre = switch(opcionFichero) {
                case 1 -> nombre += ".txt";
                case 2 -> nombre += ".dat";
                case 3 -> nombre += ".xml";
                default -> nombre += "";
            };
            File archivoNuevo = new File(nombre);
            archivo.renameTo(archivoNuevo);
            archivo.delete();
            return archivoNuevo;
        }
    }
}
