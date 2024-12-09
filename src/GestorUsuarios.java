
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


public class GestorUsuarios {
    private static final String RUTA_USUARIOS = "usuarios.dat";

    private ArrayList<Usuario> listaUsuarios;

    public GestorUsuarios() {
        listaUsuarios = new ArrayList<>();
        cargarUsuarios();
    }

    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {
        File f = new File(RUTA_USUARIOS);
        if(!f.exists()) {
            return; // no hay usuarios
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            listaUsuarios = (ArrayList<Usuario>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_USUARIOS))) {
            oos.writeObject(listaUsuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registrarUsuario(String nombre, String password, boolean esAdmin) {
        if(buscarUsuario(nombre) != null) return false;
        Usuario u = new Usuario(nombre, password, esAdmin);
        listaUsuarios.add(u);
        guardarUsuarios();
        return true;
    }

    public Usuario buscarUsuario(String nombre) {
        for(Usuario u : listaUsuarios) {
            if(u.getNombre().equalsIgnoreCase(nombre)) return u;
        }
        return null;
    }

    public Usuario login(String nombre, String password) {
        Usuario u = buscarUsuario(nombre);
        if(u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    public void actualizarUsuario(Usuario u) {
        // Actualizamos la info del usuario en la lista
        for(int i=0; i< listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNombre().equalsIgnoreCase(u.getNombre())){
                listaUsuarios.set(i,u);
                break;
            }
        }
        guardarUsuarios();
    }

    public ArrayList<Usuario> getUsuarios() {
        return listaUsuarios;
    }
}
