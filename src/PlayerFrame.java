
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class PlayerFrame extends JFrame {
    private ReproductorMusica reproductor;
    private JButton btnDetener;

    public PlayerFrame(String rutaArchivo) {
        setTitle("Reproduciendo: " + rutaArchivo);
        setSize(300,100);
        setLocationRelativeTo(null);

        reproductor = new ReproductorMusica();
        reproductor.reproducir(rutaArchivo);

        btnDetener = new JButton("Detener");
        add(btnDetener);

        btnDetener.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reproductor.detener();
                dispose();
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter(){
            public void windowClosing(java.awt.event.WindowEvent e) {
                reproductor.detener();
            }
        });
    }
}
