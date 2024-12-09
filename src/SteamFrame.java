
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
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


public class SteamFrame extends JFrame {
    private Usuario usuario;
    private GestorUsuarios gestorUsuarios;
    private GestorJuegosGlobal gestorJuegosGlobal;

    private JTable tabla;
    private JButton btnAgregarGlobal, btnAgregarBiblioteca;
    private DefaultTableModel modeloTabla;
    private JLabel lblCover; // Para mostrar la carátula del juego

    public SteamFrame(Usuario u, GestorUsuarios gu) {
        this.usuario = u;
        this.gestorUsuarios = gu;
        this.gestorJuegosGlobal = new GestorJuegosGlobal();

        setTitle("Steam - " + u.getNombre());
        setSize(700,400);
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel(new Object[]{"Nombre","Género","Desarrollador","Lanzamiento","Ruta"},0);
        tabla = new JTable(modeloTabla);

        lblCover = new JLabel();
        lblCover.setPreferredSize(new Dimension(200,200));
        lblCover.setHorizontalAlignment(SwingConstants.CENTER);
        lblCover.setBorder(BorderFactory.createTitledBorder("Carátula"));

        cargarTablaGlobal();

        btnAgregarGlobal = new JButton("Agregar Juego (Admin)");
        btnAgregarBiblioteca = new JButton("Agregar a Mi Biblioteca");

        if(!usuario.isAdmin()) {
            btnAgregarGlobal.setEnabled(false);
        }

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregarGlobal);
        panelBotones.add(btnAgregarBiblioteca);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panelCentral.add(lblCover, BorderLayout.EAST);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregarGlobal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usuario.isAdmin()) {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Seleccionar archivo del juego");
                    int result = fc.showOpenDialog(SteamFrame.this);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        // Se piden datos adicionales
                        String nombre = JOptionPane.showInputDialog("Nombre del juego:");
                        String genero = JOptionPane.showInputDialog("Género:");
                        String desarrollador = JOptionPane.showInputDialog("Desarrollador:");
                        String fecha = JOptionPane.showInputDialog("Fecha de Lanzamiento (YYYY-MM-DD):");

                        // Seleccionar la carátula
                        JFileChooser fcCover = new JFileChooser();
                        fcCover.setDialogTitle("Seleccionar imagen de carátula del juego");
                        int coverRes = fcCover.showOpenDialog(SteamFrame.this);
                        String coverPath = "";
                        if(coverRes == JFileChooser.APPROVE_OPTION) {
                            coverPath = fcCover.getSelectedFile().getAbsolutePath();
                        }

                        if(coverPath.isEmpty()) {
                            JOptionPane.showMessageDialog(SteamFrame.this,"No se seleccionó carátula, se procederá sin imagen");
                        }

                        Juego j = new Juego(nombre, genero, desarrollador, fecha, f.getAbsolutePath(), coverPath);
                        gestorJuegosGlobal.agregarJuego(j);
                        cargarTablaGlobal();
                    }
                }
            }
        });

        btnAgregarBiblioteca.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tabla.getSelectedRow();
                if(row >= 0) {
                    ArrayList<Juego> global = gestorJuegosGlobal.getJuegosGlobal();
                    Juego j = global.get(row);
                    usuario.agregarJuego(j);
                    gestorUsuarios.actualizarUsuario(usuario);
                    JOptionPane.showMessageDialog(SteamFrame.this, "Agregado a tu biblioteca de juegos");
                }
            }
        });

        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tabla.getSelectedRow();
                if(row >= 0) {
                    Juego j = gestorJuegosGlobal.getJuegosGlobal().get(row);
                    mostrarCaratula(j.getCoverPath());
                }
            }
        });
    }

    private void cargarTablaGlobal() {
        modeloTabla.setRowCount(0);
        for(Juego j : gestorJuegosGlobal.getJuegosGlobal()) {
            modeloTabla.addRow(new Object[]{j.getNombre(), j.getGenero(), j.getDesarrollador(), j.getFechaLanzamiento(), j.getRutaInstalacion()});
        }
    }

    private void mostrarCaratula(String path) {
        if(path == null || path.isEmpty()) {
            lblCover.setIcon(null);
            lblCover.setText("Sin carátula");
            return;
        }
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        lblCover.setIcon(new ImageIcon(img));
        lblCover.setText("");
    }
}
