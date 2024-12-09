
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

public class GestorMusicaGlobal {
    private static final String RUTA_MUSICA_GLOBAL = "musicaGlobal.dat";
    private ArrayList<Musica> musicaGlobal;

    public GestorMusicaGlobal() {
        musicaGlobal = new ArrayList<>();
        cargarMusicaGlobal();
    }

    @SuppressWarnings("unchecked")
    private void cargarMusicaGlobal() {
        File f = new File(RUTA_MUSICA_GLOBAL);
        if(!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            musicaGlobal = (ArrayList<Musica>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarMusicaGlobal() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_MUSICA_GLOBAL))) {
            oos.writeObject(musicaGlobal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarMusica(Musica m) {
        musicaGlobal.add(m);
        guardarMusicaGlobal();
    }

    public ArrayList<Musica> getMusicaGlobal() {
        return musicaGlobal;
    }
}
