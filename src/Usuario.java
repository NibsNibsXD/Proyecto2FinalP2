
import java.io.*;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */
 


public class Usuario implements Serializable {
    private String nombre;
    private String password;
    private boolean esAdmin;

    private ArrayList<Musica> bibliotecaMusical;
    private ArrayList<Juego> bibliotecaJuegos;
    private ArrayList<MensajeChat> historialChat;

    public Usuario(String nombre, String password, boolean esAdmin) {
        this.nombre = nombre;
        this.password = password;
        this.esAdmin = esAdmin;
        this.bibliotecaMusical = new ArrayList<>();
        this.bibliotecaJuegos = new ArrayList<>();
        this.historialChat = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return esAdmin; }

    public ArrayList<Musica> getBibliotecaMusical() { return bibliotecaMusical; }
    public ArrayList<Juego> getBibliotecaJuegos() { return bibliotecaJuegos; }

    public void agregarMusica(Musica m) {
        bibliotecaMusical.add(m);
    }

    public void agregarJuego(Juego j) {
        bibliotecaJuegos.add(j);
    }

    public void agregarMensajeChat(MensajeChat msg) {
        historialChat.add(msg);
    }

    public ArrayList<MensajeChat> getHistorialChat() {
        return historialChat;
    }

    @Override
    public String toString() {
        return nombre + (esAdmin ? " (Admin)" : "");
    }
}
