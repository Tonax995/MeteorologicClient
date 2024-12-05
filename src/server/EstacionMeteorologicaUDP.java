package server;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

public class EstacionMeteorologicaUDP {
    private static Map<String, Integer> clientes = new HashMap<>();

    public static void main(String[] args) {
        try {
            DatagramSocket solicitudSocket = new DatagramSocket(9091);
            DatagramSocket envioSocket = new DatagramSocket();
            System.out.println("Estación meteorológica iniciada y esperando solicitudes de clientes...");

            // Hilo para manejar solicitudes de clientes
            Thread manejoSolicitudes = new Thread(() -> {
                try {
                    while (true) {
                        byte[] buffer = new byte[1024];
                        DatagramPacket solicitud = new DatagramPacket(buffer, buffer.length);
                        solicitudSocket.receive(solicitud);
                        String mensaje = new String(solicitud.getData(), 0, solicitud.getLength());
                        String ipCliente = solicitud.getAddress().getHostAddress();
                        int puertoCliente = solicitud.getPort(); // Obtiene el puerto del cliente desde el paquete

                        System.out.println("Solicitud recibida de: " + ipCliente + ":" + puertoCliente);

                        // Añadir la IP y puerto del cliente a la lista si no está ya presente
                        if (!clientes.containsKey(ipCliente)) {
                            clientes.put(ipCliente, puertoCliente);
                            System.out.println("Cliente agregado a la whitelist: " + ipCliente + ":" + puertoCliente);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            manejoSolicitudes.start();

            // Hilo para enviar datos de clima a los clientes en la whitelist
            Thread enviarDatos = new Thread(() -> {
                try {
                    Random random = new Random();
                    while (true) {
                        // Generar datos de clima utilizando la clase DatosMeteorologicos
                        DatosMeteorologicos datos = new DatosMeteorologicos();
                        JSONObject datosClima = datos.generarDatosJSON();

                        // Convertir el objeto JSON a una cadena
                        String mensaje = datosClima.toString();
                        byte[] buffer = mensaje.getBytes();

                        // Enviar el mensaje a cada cliente en la lista de direcciones IP
                        for (Map.Entry<String, Integer> cliente : clientes.entrySet()) {
                            String ipCliente = cliente.getKey();
                            int puertoCliente = cliente.getValue();

                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ipCliente), puertoCliente);
                            envioSocket.send(packet);
                            System.out.println("Enviando datos a " + ipCliente + ":" + puertoCliente + " - " + mensaje);
                        }

                        // Esperar 5 segundos antes de enviar la siguiente lectura
                        Thread.sleep(5000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            enviarDatos.start();

            // Mantener la aplicación principal en ejecución
            manejoSolicitudes.join();
            enviarDatos.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}