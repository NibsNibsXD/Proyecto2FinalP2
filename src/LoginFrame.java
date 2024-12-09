
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

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegistro;
    private GestorUsuarios gestor;

    public LoginFrame() {
        gestor = new GestorUsuarios();
        setTitle("Login");
        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnLogin = new JButton("Login");
        btnRegistro = new JButton("Registrarse");

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Password:"));
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnRegistro);

        add(panel);

        btnLogin.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUsuario.getText();
                String pass = new String(txtPassword.getPassword());
                Usuario u = gestor.login(user, pass);
                if(u != null) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login exitoso!");
                    new MainMenuFrame(u, gestor).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Usuario o password incorrectos");
                }
            }
        });

        btnRegistro.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUsuario.getText();
                String pass = new String(txtPassword.getPassword());
                if(user.isEmpty() || pass.isEmpty()){
                    JOptionPane.showMessageDialog(LoginFrame.this,"Complete todos los campos");
                    return;
                }
                // Asumiremos que si el nombre inicia con "admin" se registra como admin (o se muestra un checkbox)
                int opt = JOptionPane.showConfirmDialog(LoginFrame.this, "¿Registrar como admin?");
                boolean esAdmin = (opt == JOptionPane.YES_OPTION);
                if(gestor.registrarUsuario(user, pass, esAdmin)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Usuario registrado!");
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "El usuario ya existe.");
                }
            }
        });
    }
}
