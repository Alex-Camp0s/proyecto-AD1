package ejercicio;

import java.util.ArrayList;
import java.util.List;

public class Gestor {

    List<Videojuego> videojuegos;

    public Gestor() {
        videojuegos = new ArrayList<>();
    }

    public void cargar(int opcion) {
        if(opcion < 1 || opcion > 3) {
            System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }
    }

    public void guardar(int opcion) {
        if(opcion < 1 || opcion > 3) {
            System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }

    }

    public void addVideojuego(Videojuego v) {
        if(v == null) {
            System.out.println("No se ha introducido el videojuego en la base de datos");
            return;
        }
        if(getVideojuego(v.getNombre()) != null) {
            System.out.println("El videojuego ya se encuentra en la base de datos");
            return;
        }
        videojuegos.add(v);
    }

    public void elimVideojuego(Videojuego v) {
        if(v == null) {
            System.out.println("El videojuego no se encuentra en la base de datos");
        }
        videojuegos.remove(v);
    }

    public void listarVideojuegos() {
        if(videojuegos.isEmpty()) {
            System.out.println("No hay ningún videojuego en la base de datos");
        }
        for(Videojuego v : videojuegos) {
            System.out.println(v);
        }
    }

    public Videojuego getVideojuego(String nombre) {
        for(Videojuego v : videojuegos) {
            if(v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }
    
}
