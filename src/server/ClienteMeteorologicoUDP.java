package server;

import java.net.*;

public class ClienteMeteorologicoUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            // Crear el socket UDP para enviar la solicitud y recibir los datos
            socket = new DatagramSocket();
            int puertoCliente = socket.getLocalPort(); // Obtener el puerto en el que se está enviando la solicitud

            // Dirección y puerto del servidor
            String servidorIP = "127.0.0.1"; // Reemplaza con la IP del servidor en ZeroTier
            int puertoServidor = 9091;

            // Enviar solicitud de registro al servidor
            String mensajeSolicitud = "SOLICITUD_REGISTRO";
            byte[] bufferSolicitud = mensajeSolicitud.getBytes();
            DatagramPacket solicitudPacket = new DatagramPacket(bufferSolicitud, bufferSolicitud.length, InetAddress.getByName(servidorIP), puertoServidor);
            socket.send(solicitudPacket);
            System.out.println("Solicitud de registro enviada al servidor desde el puerto: " + puertoCliente);

            // Cerrar el socket para reenlazar en el mismo puerto
            socket.close();
            socket = new DatagramSocket(puertoCliente); // Reutilizar el puerto obtenido para escuchar los datos

            byte[] buffer = new byte[1024];

            System.out.println("Cliente escuchando datos meteorológicos en el puerto: " + puertoCliente);

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
