
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

public class MainMenuFrame extends JFrame {
    private JButton btnSteam, btnSpotify, btnPerfil, btnChat;
    private Usuario usuario;
    private GestorUsuarios gestorUsuarios;
    private GestorMusicaGlobal gestorMusicaGlobal;
    private GestorChat gestorChat;

    public MainMenuFrame(Usuario u, GestorUsuarios gu) {
        this.usuario = u;
        this.gestorUsuarios = gu;
        this.gestorMusicaGlobal = new GestorMusicaGlobal();
        this.gestorChat = new GestorChat();

        setTitle("Menú Principal - Bienvenido " + u.getNombre());
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        btnSteam = new JButton("Ir a STEAM");
        btnSpotify = new JButton("Ir a SPOTIFY");
        btnPerfil = new JButton("Mi Perfil");
        btnChat = new JButton("Chat");

        panel.add(btnSteam);
        panel.add(btnSpotify);
        panel.add(btnPerfil);
        panel.add(btnChat);

        add(panel);

        // Acciones
        btnSteam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar ventana de juegos
                new SteamFrame(usuario, gestorUsuarios).setVisible(true);
            }
        });

        btnSpotify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SpotifyFrame(usuario, gestorUsuarios, gestorMusicaGlobal).setVisible(true);
            }
        });

        btnPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PerfilFrame(usuario, gestorUsuarios).setVisible(true);
            }
        });

        btnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChatFrame(usuario, gestorUsuarios, gestorChat).setVisible(true);
            }
        });

    }
}
