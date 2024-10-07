package ejercicio_final;

import java.io.Serializable;

/**
 * Clase videojuego que representa los datos básicos de un videojuego tales pueden ser su nombre, el año en el que se
 * publicó, la nota que le da el usuario y si está disponible en consola.
 * <p>
 * Implementa la interfaz Serializable.
 *
 * @author Alejandro Campos
 * @version 03102024A
 */
public class Videojuego implements Serializable {

    private String nombre;
    private int anoPublicacion;
    private double nota;
    private boolean disponibleEnConsola;

    /**
     * Constructor vacío para poder instanciar un objeto Videojuego sin necesidad de introducir ningún dato.<p> Al
     * instanciar un objeto desde este constructor se deben usar los setters para introducirle los datos al objeto.
     */
    public Videojuego() {
        this.nombre = "";
        this.anoPublicacion = 0;
        this.nota = 0;
        this.disponibleEnConsola = false;
    }

    /**
     * Constructor que pide los datos básicos de un Videojuego, en este caso solo pide el nombre del videojuego. El
     * resto de atributos del objeto se inicializan a 0 en caso de que no haya error.
     * <p>
     * Hace las comprobaciones necesarias para que los datos introducidos sean válidos. Si uno o más de uno de los datos
     * del videojuego no es válido no se creará el objeto.
     *
     * @param nombre nombre del videojuego. No debe estar vacío o ser nulo.
     *
     * @throws IllegalArgumentException si el dato introducido no es válido según lo indicado anteriormente.
     */
    public Videojuego(String nombre) {
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Error: No ha introducido el nombre del videojuego");
        }
        this.nombre = nombre;
        this.anoPublicacion = 0;
        this.nota = 0;
        this.disponibleEnConsola = false;
    }

    /**
     * Constructor que pide todos los datos de un Videojuego. Se llama al constructor que solo pide al nombre y a los
     * setters para incializar los datos del objeto. El objeto no se creará si uno o más datos son inválidos.
     *
     * @param nombre              nombre del videojuego. No debe estar vacío o ser nulo.
     * @param anoPublicacion      año en el que se publicó el videojuego. No debe ser anterior a 1972.
     * @param nota                nota que le da el usuario al videojuego. Debe estar comprendida entre 0 y 10.
     * @param disponibleEnConsola si el videojuego está disponible para consolas.
     *
     * @throws IllegalArgumentException si cualquiera de los datos introducidos no es válido según lo indicado
     *                                  anteriormente.
     */
    public Videojuego(String nombre, int anoPublicacion, double nota, boolean disponibleEnConsola) {
        this(nombre);
        setAnoPublicacion(anoPublicacion);
        setNota(nota);
        setDisponibleEnConsola(disponibleEnConsola);
    }

    /**
     * Metodo que devuelve el nombre del videojuego.
     *
     * @return nombre del videojuego.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo que actualiza el nombre del videojuego según el nombre que introduce el usuario.<br>
     * Se comprueba que sea un nombre válido.
     *
     * @param nombre nombre del videojuego. No debe estar vacío o ser nulo.
     *
     * @throws IllegalArgumentException si el nombre introducido no cumple los requisitos previamente mencionados.
     */
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("Error: No ha introducido el nombre del videojuego");
        }
        this.nombre = nombre;
    }

    /**
     * Metodo que devuelve la nota que le ha puesto el usuario al videojuego.
     *
     * @return nota del usuario para el videojuego.
     */
    public double getNota() {
        return nota;
    }

    /**
     * Metodo que actualiza la nota que le ha puesto el usuario al videojuego. <br>
     * Comprueba que la nota esté entre 0 y 10.
     *
     * @param nota nota del usuario para el videojuego.
     *
     * @throws IllegalArgumentException si la nota no está entre 0 y 10.
     */
    public void setNota(double nota) {
        if(nota < 0.0 || nota > 10.0) {
            throw new IllegalArgumentException("Error: La nota debe estar comprendida entre 0 y 10");
        }
        this.nota = nota;
    }

    /**
     * Metodo que devuelve el año en el que se publicó el videojuego.
     *
     * @return año en el que se publicó el videojuego.
     */
    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    /**
     * Metodo que actualiza el año en el que se publicó el videojuego. <br>
     * Comprueba que no sea inferior a 1972.
     *
     * @param anoPublicacion año en el que se publicó el videojuego.
     *
     * @throws IllegalArgumentException si el año introducido es menor que 1972.
     */
    public void setAnoPublicacion(int anoPublicacion) {
        if(anoPublicacion < 1972) {
            throw new IllegalArgumentException("Error: El videojuego no puede ser anterior a 1972");
        }
        this.anoPublicacion = anoPublicacion;
    }

    /**
     * Metodo que devuelve si el videojuego está disponible para consolas
     *
     * @return <code>true</code> si está disponible para consolas, <code>false</code> si no.
     */
    public boolean isDisponibleEnConsola() {
        return disponibleEnConsola;
    }

    /**
     * Metodo que actualiza si el videojuego está disponible para consola.
     *
     * @param disponibleEnConsola si está disponible para consola o no.
     */
    public void setDisponibleEnConsola(boolean disponibleEnConsola) {
        this.disponibleEnConsola = disponibleEnConsola;
    }

    /**
     * Metodo que comprueba si un objeto es igual que otro.
     * Para que dos videojuegos sean iguales deben de tener el mismo nombre sin importar las mayusculas o minusculas.
     *
     * @param v videojuego a comparar si es igual.
     *
     * @return true si son el mismo videojuego, false si no son el mismo videojuego.
     */
    public boolean equals(Videojuego v) {
        return nombre.equalsIgnoreCase(v.nombre);
    }

    /**
     * Metodo que crea un código hash que identifica al objeto.
     *
     * @return codigo hash del objeto
     */
    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    /**
     * Metodo que devuelve los datos del objeto en un String que le permite al usuario leerlo correctamente.
     *
     * @return String con los datos del objeto.
     */
    @Override
    public String toString() {
        String consola = disponibleEnConsola ? "sí" : "no";
        return "Título: " + nombre + " publicado en " + anoPublicacion + " puntuado con " + nota + " puntos y " +
               consola + " está disponible para consolas";
    }
}
