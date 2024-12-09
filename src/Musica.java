
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */

public class Musica implements Serializable {
    private String titulo;
    private String artista;
    private String album;
    private int duracion; // en segundos
    private String rutaArchivo;
    private String coverPath;  // Nueva ruta para la imagen de carátula

    public Musica(String titulo, String artista, String album, int duracion, String rutaArchivo, String coverPath) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
        this.rutaArchivo = rutaArchivo;
        this.coverPath = coverPath;
    }

    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public String getAlbum() { return album; }
    public int getDuracion() { return duracion; }
    public String getRutaArchivo() { return rutaArchivo; }
    public String getCoverPath() { return coverPath; }

    @Override
    public String toString() {
        return titulo + " - " + artista + " (" + album + ")";
    }
}
