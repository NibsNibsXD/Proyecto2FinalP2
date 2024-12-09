

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */




public class SpotifyFrame extends JFrame {
    private Usuario usuario;
    private GestorUsuarios gestorUsuarios;
    private GestorMusicaGlobal gestorMusicaGlobal;

    private JTable tabla;
    private JButton btnAgregarGlobal, btnAgregarFavoritos, btnReproducir;
    private DefaultTableModel modeloTabla;
    private JLabel lblCover; // Etiqueta para mostrar la carátula

    public SpotifyFrame(Usuario u, GestorUsuarios gu, GestorMusicaGlobal gmg) {
        this.usuario = u;
        this.gestorUsuarios = gu;
        this.gestorMusicaGlobal = gmg;

        setTitle("Spotify - " + u.getNombre());
        setSize(700,400);
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel(new Object[]{"Título","Artista","Álbum","Duración"},0);
        tabla = new JTable(modeloTabla);

        lblCover = new JLabel();
        lblCover.setPreferredSize(new Dimension(200,200));
        lblCover.setHorizontalAlignment(SwingConstants.CENTER);
        lblCover.setBorder(BorderFactory.createTitledBorder("Carátula"));

        cargarTablaGlobal();

        btnAgregarGlobal = new JButton("Agregar Música (Admin)");
        btnAgregarFavoritos = new JButton("Agregar a Mi Biblioteca");
        btnReproducir = new JButton("Reproducir");

        if(!u.isAdmin()) {
            btnAgregarGlobal.setEnabled(false);
        }

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarGlobal);
        panelBotones.add(btnAgregarFavoritos);
        panelBotones.add(btnReproducir);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelCentral.add(lblCover, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregarGlobal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(u.isAdmin()){
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Seleccionar archivo de música");
                    int result = fc.showOpenDialog(SpotifyFrame.this);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        // Pedimos datos
                        String titulo = JOptionPane.showInputDialog("Título");
                        String artista = JOptionPane.showInputDialog("Artista");
                        String album = JOptionPane.showInputDialog("Álbum");
                        int duracion = Integer.parseInt(JOptionPane.showInputDialog("Duración (segundos)"));

                        // Ahora pedimos la imagen de la carátula
                        JFileChooser fcCover = new JFileChooser();
                        fcCover.setDialogTitle("Seleccionar imagen de carátula");
                        int resCover = fcCover.showOpenDialog(SpotifyFrame.this);
                        String coverPath = null;
                        if(resCover == JFileChooser.APPROVE_OPTION) {
                            File coverFile = fcCover.getSelectedFile();
                            coverPath = coverFile.getAbsolutePath();
                        }

                        if(coverPath == null) {
                            JOptionPane.showMessageDialog(SpotifyFrame.this,"No se seleccionó carátula, se procederá sin imagen");
                            coverPath = ""; // vacío si no hay
                        }

                        Musica m = new Musica(titulo, artista, album, duracion, f.getAbsolutePath(), coverPath);
                        gestorMusicaGlobal.agregarMusica(m);
                        cargarTablaGlobal();
                    }
                }
            }
        });

        btnAgregarFavoritos.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                if(row >= 0) {
                    ArrayList<Musica> global = gestorMusicaGlobal.getMusicaGlobal();
                    Musica m = global.get(row);
                    usuario.agregarMusica(m);
                    gestorUsuarios.actualizarUsuario(usuario);
                    JOptionPane.showMessageDialog(SpotifyFrame.this, "Agregado a tu biblioteca");
                }
            }
        });

        btnReproducir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                if(row >= 0) {
                    Musica m = gestorMusicaGlobal.getMusicaGlobal().get(row);
                    // Reproducir con JLayer
                    new PlayerFrame(m.getRutaArchivo()).setVisible(true);
                }
            }
        });

        // Listener para mostrar la carátula al seleccionar una fila
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tabla.getSelectedRow();
                if(row >= 0) {
                    Musica m = gestorMusicaGlobal.getMusicaGlobal().get(row);
                    mostrarCaratula(m.getCoverPath());
                }
            }
        });
    }

    private void cargarTablaGlobal() {
        modeloTabla.setRowCount(0);
        for(Musica m : gestorMusicaGlobal.getMusicaGlobal()) {
            modeloTabla.addRow(new Object[]{m.getTitulo(), m.getArtista(), m.getAlbum(), m.getDuracion()});
        }
    }

    private void mostrarCaratula(String path) {
        if(path == null || path.isEmpty()) {
            lblCover.setIcon(null);
            lblCover.setText("Sin carátula");
            return;
        }
        ImageIcon icon = new ImageIcon(path);
        // Opcional: Redimensionar
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblCover.setIcon(new ImageIcon(img));
        lblCover.setText("");
    }
}
