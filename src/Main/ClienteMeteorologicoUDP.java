/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author tonar
 */
import java.net.*;

import java.net.*;

public class ClienteMeteorologicoUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Crear el socket UDP para enviar la solicitud y recibir los datos
            socket = new DatagramSocket();

            // Dirección y puerto del servidor
            String servidorIP = "10.147.20.86"; // Reemplaza con la IP del servidor en ZeroTier
            int puertoServidor = 9090;

            // Enviar solicitud de registro al servidor
            String mensajeSolicitud = "SOLICITUD_REGISTRO";
            byte[] bufferSolicitud = mensajeSolicitud.getBytes();
            DatagramPacket solicitudPacket = new DatagramPacket(bufferSolicitud, bufferSolicitud.length, InetAddress.getByName(servidorIP), puertoServidor);
            socket.send(solicitudPacket);
            System.out.println("Solicitud de registro enviada al servidor.");

            // Escuchar en el puerto 9090 para recibir datos meteorológicos
            socket = new DatagramSocket(9090);
            byte[] buffer = new byte[1024];

            System.out.println("Cliente escuchando datos meteorológicos...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String mensaje = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Datos recibidos: " + mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
     }
}
}
