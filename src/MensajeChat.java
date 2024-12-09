
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jorge Aguirre
 */


public class MensajeChat implements Serializable {
    private String mensaje;
    private String remitente;
    private String destinatario; // "GLOBAL" para mensajes globales, o nombre del usuario destinatario para DM
    private String timestamp;

    public MensajeChat(String mensaje, String remitente, String destinatario) {
        this.mensaje = mensaje;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getMensaje() { return mensaje; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + remitente + " -> " + destinatario + ": " + mensaje;
    }
}
