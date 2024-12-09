
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class Juego implements Serializable {
    private String nombre;
    private String genero;
    private String desarrollador;
    private String fechaLanzamiento;
    private String rutaInstalacion;
    private String coverPath; // Nueva ruta para la imagen de carátula

    public Juego(String nombre, String genero, String desarrollador, String fechaLanzamiento, String rutaInstalacion, String coverPath) {
        this.nombre = nombre;
        this.genero = genero;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.rutaInstalacion = rutaInstalacion;
        this.coverPath = coverPath;
    }

    public String getNombre() { return nombre; }
    public String getGenero() { return genero; }
    public String getDesarrollador() { return desarrollador; }
    public String getFechaLanzamiento() { return fechaLanzamiento; }
    public String getRutaInstalacion() { return rutaInstalacion; }
    public String getCoverPath() { return coverPath; }

    @Override
    public String toString() {
        return nombre + " (" + genero + ") - " + desarrollador;
    }
}
