
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



public class GestorJuegosGlobal {
    private static final String RUTA_JUEGOS_GLOBAL = "juegosGlobal.dat";
    private ArrayList<Juego> juegosGlobal;

    public GestorJuegosGlobal() {
        juegosGlobal = new ArrayList<>();
        cargarJuegosGlobal();
    }

    @SuppressWarnings("unchecked")
    private void cargarJuegosGlobal() {
        File f = new File(RUTA_JUEGOS_GLOBAL);
        if(!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            juegosGlobal = (ArrayList<Juego>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarJuegosGlobal() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_JUEGOS_GLOBAL))) {
            oos.writeObject(juegosGlobal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarJuego(Juego j) {
        juegosGlobal.add(j);
        guardarJuegosGlobal();
    }

    public ArrayList<Juego> getJuegosGlobal() {
        return juegosGlobal;
    }
}
