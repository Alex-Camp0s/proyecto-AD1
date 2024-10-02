package ejercicio;

import java.util.ArrayList;
import java.util.List;

public class Gestor {

    List<Videojuego> videojuegos;

    public Gestor(){
        videojuegos = new ArrayList<>();
    }

    public void cargar(int opcion){
        if(opcion < 1 || opcion > 3){
            System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }
    }
    public void guardar(int opcion){
        if(opcion < 1 || opcion > 3){
            System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }

    }
    public void addVideojuego(){
        Videojuego v = new Videojuego();
        videojuegos.add(v);
        System.out.println("Falta implementar");
    }
    public void elimVideojuego(){
        Videojuego v = new Videojuego();
        videojuegos.remove(v);
        System.out.println("Falta implementar");
    }
    public void listarVideojuegos(){
        for(Videojuego v: videojuegos){
            System.out.println(v);
        }
    }
}
