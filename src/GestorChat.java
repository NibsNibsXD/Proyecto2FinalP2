
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



public class GestorChat {
    private static final String RUTA_CHAT_GLOBAL = "chatGlobal.dat";
    private ArrayList<MensajeChat> mensajesGlobal;

    public GestorChat() {
        mensajesGlobal = new ArrayList<>();
        cargarMensajes();
    }

    @SuppressWarnings("unchecked")
    private void cargarMensajes() {
        File f = new File(RUTA_CHAT_GLOBAL);
        if(!f.exists()) return;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            mensajesGlobal = (ArrayList<MensajeChat>) ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void guardarMensajes() {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_CHAT_GLOBAL))) {
            oos.writeObject(mensajesGlobal);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensajeGlobal(MensajeChat msg) {
        mensajesGlobal.add(msg);
        guardarMensajes();
    }

    public ArrayList<MensajeChat> getMensajesGlobal() {
        return mensajesGlobal;
    }
}
