
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class ChatFrame extends JFrame {
    private Usuario usuario;
    private GestorUsuarios gestorUsuarios;
    private GestorChat gestorChat;

    // Componentes de la pestaña de Chat Global
    private JTextArea areaChatGlobal;
    private JTextField txtMensajeGlobal;
    private JButton btnEnviarGlobal;

    // Componentes de la pestaña de DM
    private JTextArea areaDM;
    private JTextField txtMensajeDM;
    private JTextField txtDestinatario;
    private JButton btnEnviarDM;

    // Botón de refrescar
    private JButton btnRefrescar;

    public ChatFrame(Usuario u, GestorUsuarios gu, GestorChat gc) {
        this.usuario = u;
        this.gestorUsuarios = gu;
        this.gestorChat = gc;

        setTitle("Chat - " + u.getNombre());
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Pestaña Chat Global
        areaChatGlobal = new JTextArea();
        areaChatGlobal.setEditable(false);
        txtMensajeGlobal = new JTextField(20);
        btnEnviarGlobal = new JButton("Enviar Global");

        JPanel panelGlobalInput = new JPanel();
        panelGlobalInput.add(txtMensajeGlobal);
        panelGlobalInput.add(btnEnviarGlobal);

        JPanel panelGlobal = new JPanel(new BorderLayout());
        panelGlobal.setBorder(BorderFactory.createTitledBorder("Chat Global"));
        panelGlobal.add(new JScrollPane(areaChatGlobal), BorderLayout.CENTER);
        panelGlobal.add(panelGlobalInput, BorderLayout.SOUTH);

        // Pestaña DM
        areaDM = new JTextArea();
        areaDM.setEditable(false);
        txtMensajeDM = new JTextField(20);
        txtDestinatario = new JTextField(10);
        btnEnviarDM = new JButton("Enviar DM");

        JPanel panelDMInput = new JPanel();
        panelDMInput.add(new JLabel("Destinatario:"));
        panelDMInput.add(txtDestinatario);
        panelDMInput.add(txtMensajeDM);
        panelDMInput.add(btnEnviarDM);

        JPanel panelDM = new JPanel(new BorderLayout());
        panelDM.setBorder(BorderFactory.createTitledBorder("Mensajes Privados (DM)"));
        panelDM.add(new JScrollPane(areaDM), BorderLayout.CENTER);
        panelDM.add(panelDMInput, BorderLayout.SOUTH);

        // JTabbedPane para separar Global y DM
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Global", panelGlobal);
        tabs.addTab("DM", panelDM);

        // Botón refrescar fuera de las pestañas
        btnRefrescar = new JButton("Refrescar");
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRefrescar);

        // Agregar todo al frame
        add(tabs, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        btnEnviarGlobal.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = txtMensajeGlobal.getText().trim();
                if(!msg.isEmpty()) {
                    MensajeChat mc = new MensajeChat(msg, usuario.getNombre(), "GLOBAL");
                    gestorChat.enviarMensajeGlobal(mc);
                    txtMensajeGlobal.setText("");
                    cargarMensajes();
                }
            }
        });

        btnEnviarDM.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = txtMensajeDM.getText().trim();
                String dest = txtDestinatario.getText().trim();
                if(!msg.isEmpty() && !dest.isEmpty()) {
                    Usuario uDest = gestorUsuarios.buscarUsuario(dest);
                    if(uDest != null) {
                        MensajeChat mc = new MensajeChat(msg, usuario.getNombre(), dest);
                        // Agregar a historial del remitente
                        usuario.agregarMensajeChat(mc);
                        gestorUsuarios.actualizarUsuario(usuario);
                        // Agregar a historial del destinatario
                        uDest.agregarMensajeChat(mc);
                        gestorUsuarios.actualizarUsuario(uDest);

                        txtMensajeDM.setText("");
                        JOptionPane.showMessageDialog(ChatFrame.this, "DM enviado a " + dest);
                        cargarMensajes();
                    } else {
                        JOptionPane.showMessageDialog(ChatFrame.this,"Destinatario no existe");
                    }
                }
            }
        });

        btnRefrescar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizar el usuario por si hay cambios
                Usuario uActualizado = gestorUsuarios.buscarUsuario(usuario.getNombre());
                if(uActualizado != null) {
                    usuario = uActualizado;
                }
                cargarMensajes();
            }
        });

        // Cargar mensajes al iniciar
        cargarMensajes();
    }

    private void cargarMensajes() {
        // Cargar mensajes globales
        areaChatGlobal.setText("");
        for(MensajeChat m : gestorChat.getMensajesGlobal()) {
            areaChatGlobal.append(m.toString() + "\n");
        }

        // Cargar mensajes DM del usuario
        areaDM.setText("");
        for(MensajeChat m : usuario.getHistorialChat()) {
            if(!m.getDestinatario().equalsIgnoreCase("GLOBAL")) {
                // Es un DM, lo mostramos indicando si fue enviado o recibido
                String dir = m.getRemitente().equalsIgnoreCase(usuario.getNombre()) 
                        ? "Enviado a " + m.getDestinatario() 
                        : "Recibido de " + m.getRemitente();
                areaDM.append("[" + m.getTimestamp() + "] " + dir + ": " + m.getMensaje() + "\n");
            }
        }
    }
}
