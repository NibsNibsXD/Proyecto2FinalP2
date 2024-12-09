
import javazoom.jl.player.Player;
import java.io.FileInputStream;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class ReproductorMusica {
    private Player player;
    private Thread playThread;

    public void reproducir(String ruta) {
        try {
            FileInputStream fis = new FileInputStream(ruta);
            player = new Player(fis);
            playThread = new Thread(() -> {
                try {
                    player.play();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });
            playThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        if(player != null) {
            player.close();
        }
        if(playThread != null && playThread.isAlive()) {
            playThread.interrupt();
        }
    }
}
