
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class PerfilFrame extends JFrame {
    private Usuario usuario;
    private GestorUsuarios gestorUsuarios;
    private JTable tablaMusica, tablaJuegos;
    private JLabel lblCoverMusica, lblCoverJuegos;

    public PerfilFrame(Usuario u, GestorUsuarios gu) {
        this.usuario = u;
        this.gestorUsuarios = gu;

        setTitle("Mi Perfil - " + u.getNombre());
        setSize(800,400);
        setLocationRelativeTo(null);

        // Musica
        DefaultTableModel modeloMusica = new DefaultTableModel(new Object[]{"Título","Artista","Álbum","Duración"},0);
        for(Musica m : u.getBibliotecaMusical()) {
            modeloMusica.addRow(new Object[]{m.getTitulo(), m.getArtista(), m.getAlbum(), m.getDuracion()});
        }
        tablaMusica = new JTable(modeloMusica);
        lblCoverMusica = new JLabel("Carátula Musica");
        lblCoverMusica.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoverMusica.setPreferredSize(new Dimension(200,200));
        lblCoverMusica.setBorder(BorderFactory.createTitledBorder("Carátula"));

        JPanel panelMusica = new JPanel(new BorderLayout());
        panelMusica.add(new JScrollPane(tablaMusica), BorderLayout.CENTER);
        panelMusica.add(lblCoverMusica, BorderLayout.EAST);

        // Juegos
        DefaultTableModel modeloJuegos = new DefaultTableModel(new Object[]{"Nombre","Género","Desarrollador","Fecha","Ruta"},0);
        for(Juego j : u.getBibliotecaJuegos()) {
            modeloJuegos.addRow(new Object[]{j.getNombre(), j.getGenero(), j.getDesarrollador(), j.getFechaLanzamiento(), j.getRutaInstalacion()});
        }
        tablaJuegos = new JTable(modeloJuegos);
        lblCoverJuegos = new JLabel("Carátula Juego");
        lblCoverJuegos.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoverJuegos.setPreferredSize(new Dimension(200,200));
        lblCoverJuegos.setBorder(BorderFactory.createTitledBorder("Carátula"));

        JPanel panelJuegos = new JPanel(new BorderLayout());
        panelJuegos.add(new JScrollPane(tablaJuegos), BorderLayout.CENTER);
        panelJuegos.add(lblCoverJuegos, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Musica", panelMusica);
        tabs.add("Juegos", panelJuegos);

        add(tabs);

        // Listener para música
        tablaMusica.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tablaMusica.getSelectedRow();
                if(row >= 0) {
                    Musica m = usuario.getBibliotecaMusical().get(row);
                    mostrarCaratula(lblCoverMusica, m.getCoverPath());
                }
            }
        });

        // Listener para juegos
        tablaJuegos.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tablaJuegos.getSelectedRow();
                if(row >= 0) {
                    Juego j = usuario.getBibliotecaJuegos().get(row);
                    mostrarCaratula(lblCoverJuegos, j.getCoverPath());
                }
            }
        });
    }

    private void mostrarCaratula(JLabel label, String path) {
        if(path == null || path.isEmpty()) {
            label.setIcon(null);
            label.setText("Sin carátula");
            return;
        }
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        label.setText("");
    }
}
