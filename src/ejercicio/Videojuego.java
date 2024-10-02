package ejercicio;

import java.io.Serializable;

public class Videojuego implements Serializable {

    private String nombre;
    private int anoPublicacion;
    private double nota;
    private boolean disponibleEnConsola;

    public Videojuego(){
    }

    public Videojuego(String nombre, int anoPublicacion,  double nota, boolean disponibleEnConsola) {
        this.nombre = nombre;
        this.disponibleEnConsola = disponibleEnConsola;
        this.nota = nota;
        this.anoPublicacion = anoPublicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(int anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public boolean isDisponibleEnConsola() {
        return disponibleEnConsola;
    }

    public void setDisponibleEnConsola(boolean disponibleEnConsola) {
        this.disponibleEnConsola = disponibleEnConsola;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "nombre='" + nombre + '\'' +
                ", anoPublicacion=" + anoPublicacion +
                ", nota=" + nota +
                ", disponibleEnConsola=" + disponibleEnConsola +
                '}';
    }
}
