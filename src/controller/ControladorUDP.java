package controller;

import model.DatosMeteorologicos;
import view.VistaDatos;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ControladorUDP {
    private VistaDatos vista;
    private DatosMeteorologicos modelo;

    public ControladorUDP(VistaDatos vista, DatosMeteorologicos modelo) {
        this.vista = vista;
        this.modelo = modelo;
        configurarListeners();
    }

    private void configurarListeners() {
        vista.setConectarListener(e -> {
            String ip = vista.getIP();
            int puerto = vista.getPuerto();
            iniciarClienteUDP(ip, puerto);
        });
    }

    public void iniciarClienteUDP(String ip, int puerto) {
        new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket(null)) {
                socket.bind(new InetSocketAddress(puerto));
                byte[] buffer = new byte[1024];

                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String receivedData = new String(packet.getData(), 0, packet.getLength());
                    procesarJSON(receivedData);
                    mostrarDatosEnVista();
                }
            } catch (Exception e) {
                vista.mostrarDatos("Error en el cliente UDP: " + e.getMessage(), "");
            }
        }).start();
    }

    private void procesarJSON(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            modelo.setTemperatura(jsonObject.getDouble("temperatura"));
            modelo.setHumedad(jsonObject.getDouble("humedad"));
            modelo.setVelocidadViento(jsonObject.getDouble("velocidadViento"));
            modelo.setDireccionViento(jsonObject.getDouble("direccionViento"));
            modelo.setPresionAtmosferica(jsonObject.getDouble("presionAtmosferica"));
            modelo.setPuntoRocio(jsonObject.getDouble("puntoRocio"));
            modelo.setIndiceUV(jsonObject.getDouble("indiceUV"));
            modelo.setCantidadPrecipitacion(jsonObject.getDouble("cantidadPrecipitacion"));
            modelo.setVisibilidad(jsonObject.getDouble("visibilidad"));
            modelo.setAltitudNubosidad(jsonObject.getDouble("altitudNubosidad"));
            modelo.setCondicionActual(jsonObject.getString("condicionActual"));
        } catch (org.json.JSONException e) {
            vista.mostrarDatos("Error al procesar JSON", e.getMessage());
        }
    }

    private void mostrarDatosEnVista() {
        vista.limpiarDatos();
        vista.mostrarDatos("Temperatura", modelo.getTemperatura() + " °C");
        vista.mostrarDatos("Humedad", modelo.getHumedad() + " %");
        vista.mostrarDatos("Velocidad del Viento", modelo.getVelocidadViento() + " km/h");
        vista.mostrarDatos("Dirección del Viento", modelo.getDireccionViento() + " °");
        vista.mostrarDatos("Presión Atmosférica", modelo.getPresionAtmosferica() + " hPa");
        vista.mostrarDatos("Punto de Rocío", modelo.getPuntoRocio() + " °C");
        vista.mostrarDatos("Índice UV", modelo.getIndiceUV() + "");
        vista.mostrarDatos("Cantidad de Precipitación", modelo.getCantidadPrecipitacion() + " mm");
        vista.mostrarDatos("Visibilidad", modelo.getVisibilidad() + " km");
        vista.mostrarDatos("Altitud de Nubosidad", modelo.getAltitudNubosidad() + " m");
        vista.mostrarDatos("Condición Actual", modelo.getCondicionActual());
    }
}
